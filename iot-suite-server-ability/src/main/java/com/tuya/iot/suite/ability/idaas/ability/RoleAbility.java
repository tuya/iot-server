package com.tuya.iot.suite.ability.idaas.ability;

import com.tuya.iot.suite.ability.idaas.model.IdaasPageResult;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleQueryReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleAbility {

    Boolean createRole(Long spaceId, IdaasRoleCreateReq request);

    Boolean updateRole(Long spaceId, String roleCode, RoleUpdateReq request);

    Boolean deleteRole(Long spaceId, String roleCode);

    IdaasRole getRole(Long spaceId, String roleCode);

    //List<IdaasRole> queryRolesByCodes(RoleQueryReq request);

    List<IdaasRole> queryRolesByUser(Long spaceId,String uid);

    IdaasPageResult<IdaasRole> queryRolesPagination(Long spaceId, RolesPaginationQueryReq req);
}
