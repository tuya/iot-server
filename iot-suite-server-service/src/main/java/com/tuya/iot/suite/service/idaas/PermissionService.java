package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionService {
    List<IdaasPermission> queryPermissionsByUser(Long spaceId, String uid);
}
