package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesRespItem;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionUpdateReq;
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
    public Boolean createPermission(Long spaceId, PermissionCreateReq permissionCreateRequest) {
        return null;
    }

    @Override
    public Boolean batchCreatePermission(Long spaceId, List<PermissionCreateReq> permissionCreateRequestList) {
        return null;
    }

    @Override
    public Boolean updatePermission(Long spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest) {
        return null;
    }

    @Override
    public Boolean deletePermission(Long spaceId, String permissionCode) {
        return null;
    }

    @Override
    public IdaasPermission getPermissionByCode(Long spaceId, String permissionCode) {
        return null;
    }

    @Override
    public List<IdaasPermission> queryPermissionsByCodes(PermissionQueryReq request) {
        return null;
    }

    @Override
    public List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(PermissionQueryByRolesReq request) {
        return null;
    }

    @Override
    public List<IdaasPermission> queryPermissionsByUser(Long spaceId, String uid) {
        //return Lists.newArrayList(IdaasPermission.builder().permissionCode("get:users").build());
        return permissionAbility.queryPermissionsByUser(spaceId,uid);
    }
}
