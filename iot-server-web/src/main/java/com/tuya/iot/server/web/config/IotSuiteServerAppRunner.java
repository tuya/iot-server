package com.tuya.iot.server.web.config;

import com.google.common.collect.Lists;
import com.tuya.connector.api.exceptions.ConnectorResultException;
import com.tuya.iot.server.ability.idaas.ability.*;
import com.tuya.iot.server.ability.idaas.model.*;
import com.tuya.iot.server.ability.user.ability.UserAbility;
import com.tuya.iot.server.ability.user.model.User;
import com.tuya.iot.server.ability.user.model.UserRegisteredRequest;
import com.tuya.iot.server.ability.user.model.UserToken;
import com.tuya.iot.server.core.util.Sha256Utils;
import com.tuya.iot.server.service.asset.AssetService;
import com.tuya.iot.server.service.dto.PermissionNodeDTO;
import com.tuya.iot.server.service.enums.RoleTypeEnum;
import com.tuya.iot.server.service.util.PermTemplateUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    UserAbility userAbility;
    @Autowired
    IdaasUserAbility idaasUserAbility;

    @Autowired
    GrantAbility grantAbility;
    @Autowired
    AssetService assetService;

    @Autowired
    PermissionAbility permissionAbility;

    String adminUserName = "admin@tuya.com";
    String adminUserId = "superAdmin";
    String adminUserPwd = Sha256Utils.getSHA256("Admin123456");
    String adminUserCountryCode = "86";

    String adminRoleCode = "admin";
    String adminRoleName = "Owner";

    String managerRoleCode = "manager-1000";
    String managerRoleName = "?????????";

    String normalRoleCode = "normal-1000";
    String normalRoleName = "????????????";

    /**
     *
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            run0(args);
        } catch (Exception e) {
            log.error("IotSuiteServerAppRunner error!", e);
        }
    }

    private void run0(ApplicationArguments args) throws Exception {
        log.info("permission-auto-init==>{}", projectProperties.permissionAutoInit);
        if (!StringUtils.isEmpty(projectProperties.getAdminUserName())) {
            adminUserName = projectProperties.getAdminUserName();
        }
        if (!StringUtils.isEmpty(projectProperties.getAdminUserPwd())) {
            adminUserPwd = projectProperties.getAdminUserPwd();
        }
        if (!StringUtils.isEmpty(projectProperties.getAdminUserCountryCode())) {
            adminUserCountryCode = projectProperties.getAdminUserCountryCode();
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
        List<PermissionNodeDTO> trees = PermTemplateUtil.loadTrees("classpath:template/permissions_zh.json", it -> true);

        if (!initPermissions(trees)) {
            log.error("init permissions failure!");
            return;
        }

        //admin
        initFromTemplate(adminRoleCode, adminUserName, adminRoleName);
        initFromTemplate(managerRoleCode, null, managerRoleName);
        initFromTemplate(normalRoleCode, null, normalRoleName);

        log.info("permission data has been initialized successful!");
    }

    private void initFromTemplate(String roleCode, String userName, String roleName) {
        if (!initRole(roleCode, roleName)) {
            log.error("init role({}) failure!", roleCode);
            return;
        }
        if (!StringUtils.isEmpty(userName)) {
            if (!initUserByRole(roleCode, userName)) {
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
        List<PermissionCreateReq> permissionNodeDTOList = PermTemplateUtil.flatten(trees).stream().map(e -> {
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
        List<String> permissionCodes = permissionNodeDTOList.stream().map(PermissionCreateReq::getPermissionCode).collect(Collectors.toList());
        List<IdaasPermission> permissionQueryReq = permissionAbility.queryPermissionsByCodes(spaceId, PermissionQueryReq.builder().permissionCodeList(permissionCodes).build());
        List<PermissionCreateReq> toAdd = new ArrayList<>();
        Map<String, String> existCodes = new HashMap<>(16);
        if (!CollectionUtils.isEmpty(permissionQueryReq)) {
            permissionQueryReq.forEach(e -> existCodes.put(e.getPermissionCode(), "exist"));
        }
        permissionCodes.forEach(e -> {
            if (!existCodes.containsKey(e)) {
                toAdd.add(allPerms.get(e));
            }
        });
        //????????????-??????
        while (toAdd.size() > 0) {
            List<PermissionCreateReq> fathers = new ArrayList<>();
            for (int i = toAdd.size() - 1; i >= 0; i--) {
                PermissionCreateReq e = toAdd.get(i);
                if (e.getParentCode() == null || existCodes.containsKey(e.getParentCode())) {
                    fathers.add(e);
                    toAdd.remove(e);
                    existCodes.put(e.getPermissionCode(), "exist");
                }
                e.setSpaceId(spaceId);
            }
            boolean addResult = permissionAbility.batchCreatePermission(spaceId, PermissionBatchCreateReq.builder().permissionList(fathers).build());
            if (!addResult) {
                log.error("add permission error!");
                return false;
            }
        }
        return true;
    }

    private boolean grantPermissionsToRole(String roleCode, List<PermissionCreateReq> perms) {
        String spaceId = projectProperties.getPermissionSpaceId();
        List<PermissionQueryByRolesRespItem>
                existsPermList = permissionAbility.queryPermissionsByRoleCodes(spaceId, PermissionQueryByRolesReq.builder()
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
        //??????????????????
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


    private boolean initUserByRole(String roleCode, String userName) {
        String spaceId = projectProperties.getPermissionSpaceId();
        String userId = null;
        //??????????????????
        try {
            UserToken userToken = userAbility.loginUser(UserRegisteredRequest.builder().username(userName)
                    .nick_name(userName).password(adminUserPwd)
                    .country_code(adminUserCountryCode).build());
            userId = userToken.getUid();
        } catch (ConnectorResultException e) {
            if ("2401".equalsIgnoreCase(e.getErrorInfo().getErrorCode())) {
                log.info("?????????????????????????????????????????????");
            }
        }
        if (userId == null) {
            try {
                User registeredUser = userAbility.registeredUser(UserRegisteredRequest.builder().username(userName)
                        .nick_name(userName).password(adminUserPwd)
                        .country_code(adminUserCountryCode).build());
                userId = registeredUser.getUser_id();
            } catch (ConnectorResultException e) {
                //???????????????????????????????????????????????????????????????????????????????????????
                if ("909".equalsIgnoreCase(e.getErrorInfo().getErrorCode())) {
                    log.info("openAPI admin user pwd changed ???init fail ==??? userName={}", userName);
                }
                return false;
            } catch (Exception e) {
                log.info("openAPI createUser uid={},username={} failure!", adminUserId, userName);
                return false;
            }
        }
        adminUserId = userId;
        projectProperties.setAdminUserId(adminUserId);
        //????????????????????????????????????
        IdaasUser user = idaasUserAbility.getUserByUid(spaceId, adminUserId);
        if (user == null) {
            Boolean userCreated = idaasUserAbility.createUser(spaceId, IdaasUserCreateReq.builder()
                    .username(userName)
                    .uid(adminUserId)
                    .remark(userName)
                    .build());
            if (!userCreated) {
                log.info("createUser uid={},username={} failure!", adminUserId, userName);
                return false;
            }
        }
        //????????????????????????????????????????????????
        IdaasPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder().roleCode(roleCode).pageNumber(1).pageSize(100).build());
        if (pageResult.getTotalCount() > 0) {
            long count = pageResult.getResults().stream().map(it -> it.getUid()).filter(it -> it.equals(adminUserId)).count();
            if (count > 0) {
                return true;
            }
        }
        //??????
        Boolean grant = grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                .spaceId(spaceId)
                .roleCode(roleCode)
                .uid(adminUserId)
                .build());
        try {
            assetService.grantAllAsset(adminUserId);
        } catch (Exception e) {
            log.error("????????????????????????", e);
        }

        return grant;
    }

    private boolean initRole(String roleCode, String roleName) {
        String spaceId = projectProperties.getPermissionSpaceId();
        IdaasRole adminRole = roleAbility.getRole(spaceId, roleCode);
        if (adminRole != null) {
            return true;
        }
        return roleAbility.createRole(spaceId, IdaasRoleCreateReq.builder()
                .roleCode(roleCode)
                .roleName(roleName)
                .remark(roleName)
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
                .authentication(projectProperties.getPermissionSpaceAuthentication())
                .remark(projectProperties.getName())
                .ownerList(Arrays.asList(projectProperties.getPermissionSpaceOwner().split(","))).build()
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
