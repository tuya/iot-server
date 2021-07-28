package com.tuya.iot.server.ability.idaas.ability;

import com.tuya.iot.server.ability.idaas.model.IdaasPageResult;
import com.tuya.iot.server.ability.idaas.model.IdaasRole;
import com.tuya.iot.server.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.server.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.server.ability.idaas.model.RolesPaginationQueryReq;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleAbility {

    Boolean createRole(String spaceId, IdaasRoleCreateReq request);

    Boolean updateRole(String spaceId, String roleCode, RoleUpdateReq request);

    Boolean deleteRole(String spaceId, String roleCode);

    IdaasRole getRole(String spaceId, String roleCode);

    //List<IdaasRole> queryRolesByCodes(RoleQueryReq request);

    List<IdaasRole> queryRolesByUser(String spaceId,String uid);

    IdaasPageResult<IdaasRole> queryRolesPagination(String spaceId, RolesPaginationQueryReq req);
}
