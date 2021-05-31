package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.service.idaas.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionAbility permissionAbility;
    @Override
    public List<IdaasPermission> queryPermissionsByUser(Long spaceId, String uid) {
        //return Lists.newArrayList(IdaasPermission.builder().permissionCode("get:users").build());
        return permissionAbility.queryPermissionsByUser(spaceId,uid);
    }
}
