package com.tuya.iot.server.ability.idaas.ability;

import com.tuya.iot.server.ability.idaas.model.*;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionAbility {
    /**
     * 新增权限
     * */
    Boolean createPermission(String spaceId, PermissionCreateReq permissionCreateRequest);

    Boolean batchCreatePermission(String spaceId, PermissionBatchCreateReq req);

    Boolean updatePermission(String spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest);

    Boolean deletePermission(String spaceId,String permissionCode);

    IdaasPermission getPermissionByCode(String spaceId, String permissionCode);

    List<IdaasPermission> queryPermissionsByCodes(String spaceId, PermissionQueryReq request);

    List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(String spaceId, PermissionQueryByRolesReq request);

    List<IdaasPermission> queryPermissionsByUser(String spaceId, String uid);


}
