package com.tuya.iot.suite.service.idaas.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.*;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.util.Assertion;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.idaas.PermissionTemplateService;
import com.tuya.iot.suite.service.idaas.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
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
    RoleService roleService;

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
        // 2. 操作者拥有该权限
        if (!perms.stream().map(it -> it.getPermissionCode()).collect(Collectors.toList()).containsAll(permissionCodes)) {
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
        }
        // 3. permissions are authorizable
        Set<String> authorizablePerms = permissionTemplateService.getAuthorizablePermissions();
        if (!authorizablePerms.containsAll(permissionCodes)) {
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
        }
    }

    @Override
    public Boolean grantPermissionsToRole(String operatorUid, RoleGrantPermissionsReq req) {
        throw new RuntimeException("this method not implements!");
        //checkForModifyPermissionToRole(req.getSpaceId(), operatorUid, req.getPermissionCodes(), req.getRoleCode());
        //return grantAbility.grantPermissionsToRole(req);
    }

    @Override
    public Boolean setPermissionsToRole(String operatorUid, RoleGrantPermissionsReq req) {
        checkForModifyPermissionToRole(req.getSpaceId(), operatorUid, req.getPermissionCodes(), req.getRoleCode());
        Set<String> existsPerms = permissionAbility.queryPermissionsByRoleCodes(req.getSpaceId(),
                PermissionQueryByRolesReq.builder()
                .roleCodeList(Lists.newArrayList(req.getRoleCode())).build()
        )
                .stream()
                .flatMap(it -> it.getPermissionList().stream())
                .map(it -> it.getPermissionCode())
                .collect(Collectors.toSet());
        Set<String> permsToSet = req.getPermissionCodes().stream().collect(Collectors.toSet());
        Set<String> permsToGrant = Sets.difference(permsToSet,existsPerms);
        Set<String> permsToRevoke = Sets.difference(existsPerms,permsToSet);
        boolean success = true;
        if(!permsToRevoke.isEmpty()){
            success &= grantAbility.revokePermissionsFromRole(RoleRevokePermissionsReq.builder()
                    .spaceId(req.getSpaceId())
                    .roleCode(req.getRoleCode())
                    .permissionCodes(permsToRevoke.stream().collect(Collectors.toList()))
                    .build());
        }
        if(!permsToGrant.isEmpty()){
            success &= grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                    .spaceId(req.getSpaceId())
                    .roleCode(req.getRoleCode())
                    .permissionCodes(permsToGrant.stream().collect(Collectors.toList()))
                    .build());
        }

        return success;
        //TODO idaas 开放了这个api再改成下面的方式
        //return grantAbility.setPermissionsToRole(req);
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
        List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId, uid);
        userRoles.forEach(it->
                Assert.isTrue(!RoleTypeEnum.fromRoleCode(it.getRoleCode()).isAdmin(), "can not change a admin user to other roles!")
        );
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
        checkForModifyRoleToUser(req.getSpaceId(), operatorUid, req.getRoleCodeList(), req.getUid());
        return grantAbility.revokeRolesFromUser(req);
    }

    @Override
    public Boolean setRoleToUsers(String spaceId, String operatorUid, String roleCode, List<String> uidList) {
        // 0. 不能把系统管理员角色设置给用户
        Assertion.isTrue(!RoleTypeEnum.fromRoleCode(roleCode).isAdmin(), "can not set 'admin' role to any users!");
        // 1. 操作者有更高级的角色（或相同的角色），才可以把这个角色设置给用户
        for (int i = uidList.size() - 1; i >= 0; i--) {
            String uid = uidList.get(i);
            List<String> newRoles = roleService.checkAndRemoveOldRole(spaceId, uid, Arrays.asList(roleCode), true);
            // 3. 逐个授权
            if (newRoles.size() > 0) {
                boolean success;
                success = grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                        .spaceId(spaceId)
                        .uid(uid)
                        .roleCode(newRoles.get(0)).build());
                if (!success) {
                    return false;
                }
            }
        }

        return true;
    }
}
