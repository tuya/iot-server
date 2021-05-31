package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleService {
    List<IdaasRole> queryRolesByUser(Long spaceId, String userId);
}
