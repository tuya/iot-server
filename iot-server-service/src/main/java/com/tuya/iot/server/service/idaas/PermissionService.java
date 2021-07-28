package com.tuya.iot.server.service.idaas;

import com.tuya.iot.server.ability.idaas.model.IdaasPermission;
import com.tuya.iot.server.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.server.ability.idaas.model.PermissionQueryByRolesReq;
import com.tuya.iot.server.ability.idaas.model.PermissionQueryByRolesRespItem;
import com.tuya.iot.server.ability.idaas.model.PermissionUpdateReq;
import com.tuya.iot.server.service.dto.PermissionNodeDTO;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionService {
    /**
     * 新增权限
     * */
    Boolean createPermission(String spaceId, PermissionCreateReq permissionCreateRequest);

    Boolean batchCreatePermission(String spaceId,List<PermissionCreateReq> permissionCreateRequestList);

    Boolean updatePermission(String spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest);

    Boolean deletePermission(String spaceId,String permissionCode);

    IdaasPermission getPermissionByCode(String spaceId, String permissionCode);

    List<IdaasPermission> queryPermissionsByCodes(String spaceId,List<String> permCodes);

    List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(String spaceId, PermissionQueryByRolesReq request);

    List<IdaasPermission> queryPermissionsByUser(String spaceId, String uid);

    List<PermissionNodeDTO> queryPermissionTrees(String permissionSpaceId, String uid);
}
