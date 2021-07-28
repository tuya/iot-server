package com.tuya.iot.server.ability.idaas.connector;

import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Query;
import com.tuya.iot.server.ability.idaas.ability.PermissionCheckAbility;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckConnector extends PermissionCheckAbility {

    @GET("/v1.0/iot-03/idaas/valid-role-permission")
    @Override
    Boolean checkPermissionForRole(@Query("spaceId") String spaceId,
                                   @Query("permissionCode") String permissionCode,
                                   @Query("roleCode") String roleCode
    );

    @POST("/v1.0/iot-03/idaas/valid-user-role")
    @Override
    Boolean checkRoleForUser(@Query("spaceId") String spaceId,
                             @Query("roleCode") String roleCode,
                             @Query("uid") String uid
    );

    @POST("/v1.0/iot-03/idaas/valid-user-permission")
    @Override
    Boolean checkPermissionForUser(@Query("spaceId") String spaceId,
                                   @Query("permission_code") String permissionCode,
                                   @Query("uid") String uid
    );
}
