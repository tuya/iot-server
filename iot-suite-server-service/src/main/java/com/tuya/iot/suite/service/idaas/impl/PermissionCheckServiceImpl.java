package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.PermissionCheckAbility;
import com.tuya.iot.suite.service.idaas.PermissionCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service("permissionCheckService")
@Slf4j
public class PermissionCheckServiceImpl implements PermissionCheckService {

    @Autowired
    PermissionCheckAbility permissionCheckAbility;

    @Override
    public Boolean checkPermissionForRole(String spaceId, String permissionCode, String roleCode) {
        return permissionCheckAbility.checkPermissionForRole(spaceId, permissionCode, roleCode);
    }

    @Override
    public Boolean checkRoleForUser(String spaceId, String roleCode, String uid) {
        return permissionCheckAbility.checkRoleForUser(spaceId, roleCode, uid);
    }

    @Override
    public Boolean checkPermissionForUser(String spaceId, String permissionCode, String userId) {
        return permissionCheckAbility.checkPermissionForUser(spaceId, permissionCode, userId);
    }
}
