package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.IdaasPageResult;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleQueryReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.model.PageVO;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleService {
    Boolean createRole(Long spaceId, RoleCreateReqDTO request);

    Boolean updateRole(Long spaceId, String roleCode, RoleUpdateReq request);

    Boolean deleteRole(Long spaceId, String roleCode);

    IdaasRole getRole(Long spaceId, String roleCode);

    List<IdaasRole> queryRolesByCodes(RoleQueryReq request);

    List<IdaasRole> queryRolesByUser(Long spaceId,String uid);

    PageVO<IdaasRole> queryRolesPagination(Long spaceId, RolesPaginationQueryReq req);

}
