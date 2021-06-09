package com.tuya.iot.suite.web.config;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.ability.*;
import com.tuya.iot.suite.ability.idaas.model.*;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
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

    private String TOP_PERMISSION_CODE = "0";


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
        List<PermissionNodeDTO> trees = PermTemplateUtil.loadTrees("classpath:template/permissions_zh.json", it->true);

        if (!initPermissions(trees)) {
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
            if (!initUserByRole(roleCode, userId, userName)) {
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

    private boolean initPermissions(List<PermissionNodeDTO> trees) {
        String spaceId = projectProperties.getPermissionSpaceId();
        List<PermissionCreateReq> permissionNodeDTOList = PermTemplateUtil.flatten(trees).stream().map(e->{
           return PermissionCreateReq.builder()
                    .name(e.getPermissionName())
                   .order(e.getOrder())
                   .parentCode(e.getParentCode())
                   .permissionCode(e.getPermissionCode())
                   .type(PermissionTypeEnum.valueOf(e.getPermissionType()).getCode())
                   .spaceId(spaceId)
                   .remark(e.getRemark())
                   .build();
        }).collect(Collectors.toList());
        Map<String, PermissionCreateReq> allPerms = permissionNodeDTOList.stream().collect(Collectors.toMap(it -> it.getPermissionCode(), it -> it));
        List<String> permissionCodes = permissionNodeDTOList.stream().map(e -> e.getPermissionCode()).collect(Collectors.toList());
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
            List<String> fatherCodes = new ArrayList<>();
            fatherCodes.add(TOP_PERMISSION_CODE);
            while (fatherCodes.size() > 0) {
                for (PermissionCreateReq e : toAdd.values()) {
                    if (fatherCodes.contains(e.getParentCode())) {
                        if (e.getParentCode().equalsIgnoreCase(TOP_PERMISSION_CODE)) {
                            e.setParentCode(null);
                        }
                        fathers.add(e);
                    }
                    e.setSpaceId(spaceId);
                }
                fatherCodes = fathers.stream().map(e -> e.getPermissionCode()).collect(Collectors.toList());
                boolean addResult = permissionAbility.batchCreatePermission(spaceId, PermissionBatchCreateReq.builder().permissionList(fathers).build());
                if (!addResult) {
                    log.error("add permission error!");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean grantPermissionsToRole(String roleCode, List<PermissionCreateReq> perms) {
        String spaceId = projectProperties.getPermissionSpaceId();
        List<PermissionQueryByRolesRespItem> existsPermList = permissionAbility.queryPermissionsByRoleCodes(spaceId, PermissionQueryByRolesReq.builder()
                .roleCodeList(Lists.newArrayList(roleCode)).build());
        Set<String> allPerms = perms.stream().map(it -> it.getPermissionCode()).collect(Collectors.toSet());
        Set<String> existsPerms = existsPermList.stream().flatMap(it -> it.getPermissionModels().stream().map(p -> p.getPermissionCode())).collect(
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
        //待删除的关联
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


    private boolean initUserByRole(String roleCode, String userId, String userName) {
        String spaceId = projectProperties.getPermissionSpaceId();
        //用户已存在？不存在则创建
        IdaasUser user = idaasUserAbility.getUserByUid(spaceId,userId);
        if(user==null){
            Boolean userCreated = idaasUserAbility.createUser(spaceId, IdaasUserCreateReq.builder()
                    .username(userName)
                    .uid(userId)
                    .remark(userName)
                    .build());
            if (!userCreated) {
                log.info("createUser uid={},username={} failure!",userId,userName);
                return false;
            }
        }
        //用户已关联角色？没关联则进行关联
        IdaasPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder().roleCode(roleCode).pageNum(1).pageSize(100).build());
        if (pageResult.getTotalCount() > 0) {
            long count = pageResult.getResults().stream().map(it->it.getUid()).filter(it->it.equals(userId)).count();
            if(count>0){
                return true;
            }
        }
        //授权
        return grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                .spaceId(spaceId)
                .roleCode(roleCode)
                .uid(userId)
                .build());
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
        if (!StringUtils.isEmpty(spaceId)) {
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
                .owner(projectProperties.getPermissionSpaceOwner()).build()
        );
        if (!StringUtils.isEmpty(spaceId)) {
            projectProperties.setPermissionSpaceId(spaceId);
            log.info("applied spaceId: {}", spaceId);
            return true;
        }
        //throw new RuntimeException("apply space failure!");
        return false;
    }

}
