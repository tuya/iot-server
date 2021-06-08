package com.tuya.iot.suite.ability.idaas.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.DELETE;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.PUT;
import com.tuya.connector.api.annotations.Path;
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
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
public interface PermissionConnector extends PermissionAbility {
    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/permissions")
    @Override
    Boolean createPermission(@Path("space_id")Long spaceId,@Body PermissionCreateReq permissionCreateRequest);

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/add-batch-permission")
    @Override
    Boolean batchCreatePermission(@Path("space_id")Long spaceId,@Body PermissionBatchCreateReq req);

    @PUT("/v1.0/iot-03/idaas/spaces/{space_id}/permissions/{permission_code}")
    @Override
    Boolean updatePermission(@Path("space_id")Long spaceId,
                             @Path("permission_code") String permissionCode,
                             @Body PermissionUpdateReq permissionUpdateRequest);

    @DELETE("/v1.0/iot-03/idaas/spaces/{space_id}/permissions/{permission_code}")
    @Override
    Boolean deletePermission(@Path("space_id")Long spaceId, @Path("permission_code") String permissionCode);

    @GET("/v1.0/iot-03/idaas/spaces/{space_id}/permissions/{permission_code}")
    @Override
    IdaasPermission getPermissionByCode(@Path("space_id")Long spaceId, @Path("permission_code") String permissionCode);

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/get-batch-permission")
    @Override
    List<IdaasPermission> queryPermissionsByCodes(@Path("space_id")Long spaceId, @Body PermissionQueryReq request);

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/get-batch-role-permission")
    @Override
    List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(@Path("space_id")Long spaceId,
            @Body PermissionQueryByRolesReq request);

    @GET("/v1.0/iot-03/idaas/spaces/{space_id}/users/{uid}/permissions")
    @Override
    List<IdaasPermission> queryPermissionsByUser(@Path("space_id") Long spaceId,
                                                 @Path("uid") String uid);
}
