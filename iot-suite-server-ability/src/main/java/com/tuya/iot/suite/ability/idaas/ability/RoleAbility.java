package com.tuya.iot.suite.ability.idaas.ability;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.RoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleQueryReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleAbility {

    Boolean createRole(Long spaceId, RoleCreateReq request);

    Boolean updateRole(Long spaceId, String roleCode, RoleUpdateReq request);

    Boolean deleteRole(Long spaceId, String roleCode);

    IdaasRole getRole(Long spaceId, String roleCode);

    List<IdaasRole> queryRolesByCodes(RoleQueryReq request);

    List<IdaasRole> queryRolesByUser(Long spaceId,String uid);
}
