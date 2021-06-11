package com.tuya.iot.suite.service.idaas.impl;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.suite.ability.idaas.model.UserRevokeRolesReq;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.idaas.PermissionTemplateService;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GrantServiceImpl implements GrantService {
    @Autowired
    GrantAbility grantAbility;
    @Autowired
    RoleAbility roleAbility;

    @Autowired
    PermissionAbility permissionAbility;

    @Autowired
    PermissionTemplateService permissionTemplateService;

    @Override
    public Boolean grantPermissionToRole(String operatorUid, RoleGrantPermissionReq req) {
        checkForModifyPermissionToRole(req.getSpaceId().toString(), operatorUid, req.getPermissionCode(), req.getRoleCode());
        return grantAbility.grantPermissionToRole(req);
    }

    private void checkForModifyPermissionToRole(String spaceId, String operatorUid, String permissionCode, String roleCode) {
        checkForModifyPermissionToRole(spaceId, operatorUid, Lists.newArrayList(permissionCode), roleCode);
    }

    private void checkForModifyPermissionToRole(String spaceId, String operatorUid, List<String> permissionCodes, String roleCode) {
        // 0. target operatorRole cannot be admin
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(roleCode).isAdmin(), "can not grant permission to a admin operatorRole!");
        List<IdaasPermission> perms = permissionAbility.queryPermissionsByUser(spaceId, operatorUid);
        List<IdaasRole> roles = roleAbility.queryRolesByUser(spaceId, operatorUid);
        Assert.isTrue(roles.size() == 1, "a user can at most have one operatorRole!");
        IdaasRole operatorRole = roles.get(0);
        // 1. 操作者角色更高级
        if (RoleTypeEnum.valueOf(operatorRole.getRoleCode()).lt(roleCode)) {
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
        }
        // 2. 操作者拥有该权限
        if (!perms.stream().map(it -> it.getPermissionCode()).collect(Collectors.toList()).containsAll(permissionCodes)) {
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
        }
        // 3. permissions are authorizable
        Set<String> authorizablePerms = permissionTemplateService.getAuthorizablePermissions();
        if (authorizablePerms.containsAll(permissionCodes)) {
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
        }
    }

    @Override
    public Boolean grantPermissionsToRole(String operatorUid, RoleGrantPermissionsReq req) {
        checkForModifyPermissionToRole(req.getSpaceId(), operatorUid, req.getPermissionCodes(), req.getRoleCode());
        return grantAbility.grantPermissionsToRole(req);
    }

    @Override
    public Boolean setPermissionsToRole(String operatorUid, RoleGrantPermissionsReq req) {
        checkForModifyPermissionToRole(req.getSpaceId(), operatorUid, req.getPermissionCodes(), req.getRoleCode());
        return grantAbility.grantPermissionsToRole(req);
    }

    @Override
    public Boolean revokePermissionFromRole(String spaceId, String operatorUid, String roleCode, String permissionCode) {
        checkForModifyPermissionToRole(spaceId, operatorUid, roleCode, permissionCode);
        return grantAbility.revokePermissionFromRole(spaceId, permissionCode, roleCode);
    }

    @Override
    public Boolean revokePermissionsFromRole(String operatorUid, RoleRevokePermissionsReq req) {
        checkForModifyPermissionToRole(req.getSpaceId().toString(), operatorUid, req.getPermissionCodes(), req.getRoleCode());
        return grantAbility.revokePermissionsFromRole(req);
    }

    @Override
    public Boolean grantRoleToUser(String operatorUid, UserGrantRoleReq req) {
        checkForModifyRoleToUser(req.getSpaceId(), operatorUid, req.getRoleCode(), req.getUid());
        return grantAbility.grantRoleToUser(req);
    }

    private void checkForModifyRoleToUser(String spaceId, String operatorUid, String roleCode, String uid) {
        checkForModifyRoleToUser(spaceId, operatorUid, Lists.newArrayList(roleCode), uid);
    }

    private void checkForModifyRoleToUser(String spaceId, String operatorUid, List<String> roleCodes, String uid) {
        roleCodes.forEach(roleCode ->
                Assert.isTrue(!RoleTypeEnum.fromRoleCode(roleCode).isAdmin(), "can not grant permission to a admin role!")
        );
        List<IdaasRole> roles = roleAbility.queryRolesByUser(spaceId, operatorUid);
        Assert.isTrue(roles.size() == 1, "a user can at most have one role!");
        IdaasRole operatorRole = roles.get(0);
        //操作者角色更高级
        roleCodes.forEach(roleCode -> {
            if (RoleTypeEnum.valueOf(operatorRole.getRoleCode()).lt(roleCode)) {
                throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
            }
        });
        //被操作的用户不能是系统管理员
        List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId, uid);
        userRoles.forEach(userRole -> {
            if (RoleTypeEnum.valueOf(operatorRole.getRoleCode()).lt(userRole.getRoleCode())) {
                throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
            }
        });
    }

    @Override
    public Boolean setRolesToUser(String operatorUid, UserGrantRolesReq req) {
        checkForModifyRoleToUser(req.getSpaceId(), operatorUid, req.getRoleCodeList(), req.getUid());
        return grantAbility.setRolesToUser(req);
    }

    @Override
    public Boolean revokeRoleFromUser(String spaceId, String operatorUid, String roleCode, String uid) {
        checkForModifyRoleToUser(spaceId, operatorUid, roleCode, uid);
        return grantAbility.revokeRoleFromUser(spaceId, roleCode, uid);
    }

    @Override
    public Boolean revokeRolesFromUser(String operatorUid, UserRevokeRolesReq req) {
        checkForModifyRoleToUser(req.getSpaceId().toString(), operatorUid, req.getRoleCodeList(), req.getUid());
        return grantAbility.revokeRolesFromUser(req);
    }

    @Override
    public Boolean setRoleToUsers(String spaceId, String operatorUid, String roleCode, List<String> uidList) {
        // 0. 不能把系统管理员角色设置给用户
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(roleCode).isAdmin(), "can not set 'admin' role to any users!");
        // 1. 操作者有更高级的角色（或相同的角色），才可以把这个角色设置给用户
        List<IdaasRole> operatorRoles = roleAbility.queryRolesByUser(spaceId, operatorUid);
        Assert.isTrue(operatorRoles.size() == 1, "a user can at most have one role!");
        String operateRoleCode = operatorRoles.get(0).getRoleCode();
        if (RoleTypeEnum.fromRoleCode(operateRoleCode).lt(roleCode)) {
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
        }
        // 2. 被设置角色的用户，如果有比操作者更高级的角色，则不允许操作。（不允许 普通员工 把 部门经理 设置为 项目经理）
        RoleTypeEnum operatorRoleType = RoleTypeEnum.fromRoleCode(operateRoleCode);

        for (int i = uidList.size() - 1; i >= 0; i--) {
            String uid = uidList.get(i);
            List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId, uid);
            Map<String, String> roleMap = userRoles.stream().collect(Collectors.toMap(it -> it.getRoleCode(), it -> it.getRoleName()));
            if (!operatorRoleType.gtEqAll(userRoles.stream().map(it -> it.getRoleCode()).collect(Collectors.toList()))) {
                throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
            }
            if (roleMap.containsKey(roleCode)) {
                uidList.remove(uid);
            } else {
                //去除原来的角色
                roleMap.values().stream().forEach(e -> {
                    Boolean removeRole = grantAbility.revokeRoleFromUser(spaceId, e, uid);
                    if (removeRole) {
                        log.info("移除了uid={} 的角色roleCode={}", uid, e);
                    }
                });
            }
        }
        // 3. 逐个授权
        if (uidList.size() > 0) {
            boolean success;
            for (String uid : uidList) {
                success = grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                        .spaceId(spaceId)
                        .uid(uid)
                        .roleCode(roleCode).build());
                if (!success) {
                    return false;
                }
            }
        }

        return true;
    }
}
