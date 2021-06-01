package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesRespItem;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionUpdateReq;

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
    Boolean createPermission(Long spaceId, PermissionCreateReq permissionCreateRequest);

    Boolean batchCreatePermission(Long spaceId,List<PermissionCreateReq> permissionCreateRequestList);

    Boolean updatePermission(Long spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest);

    Boolean deletePermission(Long spaceId,String permissionCode);

    IdaasPermission getPermissionByCode(Long spaceId, String permissionCode);

    List<IdaasPermission> queryPermissionsByCodes(PermissionQueryReq request);

    List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(PermissionQueryByRolesReq request);

    List<IdaasPermission> queryPermissionsByUser(Long spaceId, String uid);
}
