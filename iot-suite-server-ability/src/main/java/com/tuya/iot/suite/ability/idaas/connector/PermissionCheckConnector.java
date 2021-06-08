package com.tuya.iot.suite.ability.idaas.connector;

import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.Query;
import com.tuya.iot.suite.ability.idaas.ability.PermissionCheckAbility;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckConnector extends PermissionCheckAbility {

    @GET("/v1.0/iot-03/idaas/valid-role-permission")
    @Override
    Boolean checkPermissionForRole(@Query("spaceId") Long spaceId,
                                   @Query("permissionCode") String permissionCode,
                                   @Query("roleCode") String roleCode
    );

    @GET("/v1.0/iot-03/idaas/valid-user-role")
    @Override
    Boolean checkRoleForUser(@Query("spaceId") Long spaceId,
                             @Query("roleCode") String roleCode,
                             @Query("uid") String uid
    );

    @GET("/v1.0/iot-03/idaas/valid-user-permission")
    @Override
    Boolean checkPermissionForUser(@Query("spaceId") Long spaceId,
                                   @Query("permission_code") String permissionCode,
                                   @Query("uid") String uid
    );
}
