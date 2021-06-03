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
    public Boolean checkPermissionForRole(Long spaceId, String roleCode, String permissionCode) {
        return permissionCheckAbility.checkPermissionForRole(spaceId,roleCode,permissionCode);
    }

    @Override
    public Boolean checkRoleForUser(Long spaceId, String uid, String roleCode) {
        return permissionCheckAbility.checkRoleForUser(spaceId,uid,roleCode);
    }

    @Override
    public Boolean checkPermissionForUser(Long spaceId, String userId, String permissionCode) {
        return permissionCheckAbility.checkPermissionForUser(spaceId,userId,permissionCode);
    }
}
