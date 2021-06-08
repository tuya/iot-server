package com.tuya.iot.suite.ability.idaas.ability;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.Path;
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.ability.idaas.model.PermissionBatchCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesRespItem;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionUpdateReq;

import java.util.Collection;
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
    Boolean createPermission(Long spaceId, PermissionCreateReq permissionCreateRequest);

    Boolean batchCreatePermission(Long spaceId, PermissionBatchCreateReq req);

    Boolean updatePermission(Long spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest);

    Boolean deletePermission(Long spaceId,String permissionCode);

    IdaasPermission getPermissionByCode(Long spaceId, String permissionCode);

    List<IdaasPermission> queryPermissionsByCodes(Long spaceId, PermissionQueryReq request);

    List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(Long spaceId,PermissionQueryByRolesReq request);

    List<IdaasPermission> queryPermissionsByUser(Long spaceId, String uid);


}
