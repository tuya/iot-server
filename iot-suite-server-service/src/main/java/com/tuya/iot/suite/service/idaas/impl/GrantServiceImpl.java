package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.suite.ability.idaas.model.UserRevokeRolesReq;
import com.tuya.iot.suite.service.idaas.GrantService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Boolean grantPermissionToRole(RoleGrantPermissionReq request) {
        return null;
    }

    @Override
    public Boolean grantPermissionsToRole(RoleGrantPermissionsReq request) {
        return grantAbility.grantPermissionsToRole(request);
    }

    @Override
    public Boolean setPermissionsToRole(RoleGrantPermissionsReq request) {
        return null;
    }

    @Override
    public Boolean revokePermissionFromRole(Long spaceId, String roleCode, String permissionCode) {
        return null;
    }

    @Override
    public Boolean revokePermissionsFromRole(RoleRevokePermissionsReq request) {
        return null;
    }

    @Override
    public Boolean grantRoleToUser(UserGrantRoleReq req) {
        return null;
    }

    @Override
    public Boolean setRolesToUser(UserGrantRolesReq req) {
        return null;
    }

    @Override
    public Boolean revokeRoleFromUser(Long spaceId, String uid, String roleCode) {
        return null;
    }

    @Override
    public Boolean revokeRolesFromUser(UserRevokeRolesReq req) {
        return null;
    }
}
