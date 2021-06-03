package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.core.model.PageVO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleService {

    Boolean createRole(Long spaceId, RoleCreateReqDTO request);

    Boolean updateRole(Long spaceId, String operatorUid, String roleCode, RoleUpdateReq request);

    Boolean deleteRole(Long spaceId, String operatorUid, String roleCode);

    IdaasRole getRole(Long spaceId, String operatorUid, String roleCode);

    List<IdaasRole> queryRolesByUser(Long spaceId,String uid);

    PageVO<IdaasRole> queryRolesPagination(Long spaceId, RolesPaginationQueryReq req);

    boolean deleteRoles(Long permissionSpaceId, String operatorUid, Collection<String> roleCodes);

    Boolean resetRolePermissionsFromTemplate(Long permissionSpaceId, String operatorUid, String roleCode);

}
