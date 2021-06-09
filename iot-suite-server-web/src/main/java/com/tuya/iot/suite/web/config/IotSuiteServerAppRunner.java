package com.tuya.iot.suite.web.config;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility;
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility;
import com.tuya.iot.suite.ability.idaas.model.*;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;
import com.tuya.iot.suite.service.util.PermTemplateUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/02
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IotSuiteServerAppRunner implements ApplicationRunner {
    @Autowired
    ProjectProperties projectProperties;
    @Autowired
    SpaceAbility spaceAbility;
    @Autowired
    RoleAbility roleAbility;
    @Autowired
    IdaasUserAbility idaasUserAbility;

    @Autowired
    GrantAbility grantAbility;

    @Autowired
    PermissionAbility permissionAbility;


    String adminUserName = "admin@tuya.com";
    String adminUserId = "superAdmin";

    String adminRoleCode = "admin";

    String manageRoleCode = "manage-1000";

    String normalRoleCode = "normal-1000";

    /**
     *
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("permission-auto-init==>{}", projectProperties.permissionAutoInit);
        if (!StringUtils.isEmpty(projectProperties.getAdminUserName())) {
            adminUserName = projectProperties.getAdminUserName();
        }
        if (!StringUtils.isEmpty(projectProperties.getAdminUserId())) {
            adminUserId = projectProperties.getAdminUserId();
        }
        if (!projectProperties.permissionAutoInit) {
            return;
        }
        //space
        if (!initPermissionSpace()) {
            log.error("apply space failure!");
            return;
        }
        //permissions
        List<PermissionCreateReq> adminPermissions = PermTemplateUtil.loadAsPermissionCreateReqList("classpath:template/permissions_zh.json", it -> it.getAuthRoleTypes().contains("admin"));

        if (!initPermissions(adminPermissions)) {
            log.error("init permissions failure!");
            return;
        }

        //admin
        initFromTemplate(adminRoleCode, adminUserId, adminUserName);
        initFromTemplate(manageRoleCode, null, null);
        initFromTemplate(normalRoleCode, null, null);

        log.info("permission data has been initialized successful!");
    }

    private void initFromTemplate(String roleCode, String userId, String userName) {
        if (!initRole(roleCode)) {
            log.error("init role({}) failure!", roleCode);
            return;
        }
        if (!StringUtils.isEmpty(userId)) {
            userId = initUserByRole(roleCode, userId, userName);
            if (StringUtils.isEmpty(userId)) {
                log.error("init user({}) failure!", roleCode);
                return;
            }

        }

        String roleType = RoleTypeEnum.fromRoleCode(roleCode).name();
        List<PermissionCreateReq> perms = PermTemplateUtil.loadAsPermissionCreateReqList("classpath:template/permissions_zh.json", it -> it.getAuthRoleTypes().contains(roleType));
        if (!grantPermissionsToRole(roleCode, perms)) {
            log.error("grant permissions to role({}) failure!", roleCode);
            return;
        }
    }

    private boolean initPermissions(List<PermissionCreateReq> perms) {
        String spaceId = projectProperties.getPermissionSpaceId();
        Map<String, PermissionCreateReq> allPerms = perms.stream().collect(Collectors.toMap(it -> it.getPermissionCode(), it -> it));
        List<String> permissionCodes = perms.stream().map(e -> e.getPermissionCode()).collect(Collectors.toList());
        List<IdaasPermission> permissionQueryReq = permissionAbility.queryPermissionsByCodes(spaceId, PermissionQueryReq.builder().permissionCodeList(permissionCodes).build());
        Map<String, PermissionCreateReq> toAdd = new HashMap<>(16);
        Map<String, IdaasPermission> existCodes = new HashMap<>(16);
        if (!CollectionUtils.isEmpty(permissionQueryReq)) {
            permissionQueryReq.stream().forEach(e -> existCodes.put(e.getPermissionCode(), e));
        }
        permissionCodes.stream().forEach(e -> {
            if (!existCodes.containsKey(e)) {
                toAdd.put(e, allPerms.get(e));
            }
        });
        if (!toAdd.isEmpty()) {
            //分级处理-父级
            List<PermissionCreateReq> fathers = new ArrayList<>();
            List<PermissionCreateReq> sons = new ArrayList<>();
            toAdd.values().stream().forEach(e -> {
                if ("0".equalsIgnoreCase(e.getParentCode())) {
                    e.setParentCode(null);
                    fathers.add(e);
                } else {
                    sons.add(e);
                }
                e.setSpaceId(spaceId);
            });
            boolean addResult = permissionAbility.batchCreatePermission(spaceId, PermissionBatchCreateReq.builder().permissionList(fathers).build());
            if (addResult) {
                addResult = addResult && permissionAbility.batchCreatePermission(spaceId, PermissionBatchCreateReq.builder().permissionList(sons).build());
            }
            if (!addResult) {
                log.error("add permission error!");
                return false;
            }
        }
        return true;
    }

    private boolean grantPermissionsToRole(String roleCode, List<PermissionCreateReq> perms) {
        String spaceId = projectProperties.getPermissionSpaceId();
        List<PermissionQueryByRolesRespItem> existsPermList = permissionAbility.queryPermissionsByRoleCodes(spaceId, PermissionQueryByRolesReq.builder()
                .roleCodeList(Lists.newArrayList(roleCode)).build());
        Set<String> allPerms = perms.stream().map(it -> it.getPermissionCode()).collect(Collectors.toSet());
        Set<String> existsPerms = existsPermList.stream().flatMap(it -> it.getPermissionList().stream().map(p -> p.getPermissionCode())).collect(
                Collectors.toSet());
        Set<String> toAdd = new HashSet<>();
        toAdd.addAll(allPerms);
        toAdd.removeAll(existsPerms);
        if (!toAdd.isEmpty()) {
            boolean addResult = grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                    .spaceId(spaceId)
                    .roleCode(roleCode)
                    .permissionCodes(Lists.newArrayList(toAdd))
                    .build());
            if (!addResult) {
                log.error("add permissions to role error!");
                return false;
            }
        }

        Set<String> toDelete = new HashSet<>();
        toDelete.addAll(existsPerms);
        toDelete.removeAll(allPerms);
        if (!toDelete.isEmpty()) {
            boolean revokeResult = grantAbility.revokePermissionsFromRole(RoleRevokePermissionsReq.builder()
                    .spaceId(spaceId)
                    .roleCode(roleCode)
                    .permissionCodes(Lists.newArrayList(toDelete))
                    .build());
            if (!revokeResult) {
                log.error("revoke permissions from role error!");
                return false;
            }
        }
        return true;
    }


    private String initUserByRole(String roleCode, String userId, String userName) {
        String spaceId = projectProperties.getPermissionSpaceId();
        IdaasPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder().roleCode(roleCode).pageNum(1).pageSize(2).build());
        if (pageResult.getTotalCount() > 0) {
            IdaasUser idaasUser = pageResult.getResults().get(0);
            return idaasUser.getUid();
        }
        Boolean userCreated = idaasUserAbility.createUser(spaceId, IdaasUserCreateReq.builder()
                .username(userName)
                .uid(userId)
                .remark(userName)
                .build());
        if (userCreated) {
            //授权
            grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                    .spaceId(spaceId)
                    .roleCode(roleCode)
                    .uid(userId)
                    .build());
            return userId;
        }
        return null;
    }

    private boolean initRole(String roleCode) {
        String spaceId = projectProperties.getPermissionSpaceId();
        IdaasRole adminRole = roleAbility.getRole(spaceId, roleCode);
        if (adminRole != null) {
            return true;
        }
        return roleAbility.createRole(spaceId, IdaasRoleCreateReq.builder()
                .roleCode(roleCode)
                .roleName(roleCode)
                .remark(roleCode)
                .build()
        );
    }

    private boolean initPermissionSpace() {
        // if spaceId has config, use it.
        String spaceId = projectProperties.getPermissionSpaceId();
        if (spaceId != null) {
            log.info("project.permission-space-id={}", spaceId);
            return true;
        }
        // else query spaceId.
        IdaasSpace idaasSpace = spaceAbility.querySpace(projectProperties.getPermissionGroup(), projectProperties.getPermissionSpaceCode());
        if (idaasSpace != null) {
            spaceId = idaasSpace.getSpaceId();
            projectProperties.setPermissionSpaceId(spaceId);
            log.info("exists spaceId {} at iot-cloud", spaceId);
            return true;
        }
        // else apply a spaceId.
        spaceId = spaceAbility.applySpace(SpaceApplyReq.builder()
                .spaceGroup(projectProperties.getPermissionGroup())
                .spaceCode(projectProperties.getPermissionSpaceCode())
                .authentication(projectProperties.getCode())
                .remark(projectProperties.getName())
                .owner(projectProperties.getName()).build()
        );
        if (spaceId != null) {
            projectProperties.setPermissionSpaceId(spaceId);
            log.info("applied spaceId: {}", spaceId);
            return true;
        }
        //throw new RuntimeException("apply space failure!");
        return false;
    }

}
