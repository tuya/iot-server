package com.tuya.iot.suite.ability.idaas.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.DELETE;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.PUT;
import com.tuya.connector.api.annotations.Query;
import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.suite.ability.idaas.model.UserRevokeRolesReq;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface GrantConnector extends GrantAbility {

    @POST("/v1.0/iot-03/idaas/role-permission")
    @Override
    Boolean grantPermissionToRole(@Body RoleGrantPermissionReq request);

    @POST("/v1.0/iot-03/idaas/role-batch-permission")
    @Override
    Boolean grantPermissionsToRole(@Body RoleGrantPermissionsReq request);

    @PUT("/v1.0/iot-03/idaas/role-batch-permission")
    @Override
    Boolean setPermissionsToRole(@Body RoleGrantPermissionsReq request);

    @DELETE("/v1.0/iot-03/idaas/role-permission")
    @Override
    Boolean revokePermissionFromRole(@Query("spaceId")String spaceId,
                                     @Query("permissionCode")String permissionCode,
                                     @Query("roleCode")String roleCode);

    @POST("/v1.0/iot-03/idaas/delete-role-batch-permission")
    @Override
    Boolean revokePermissionsFromRole(@Body RoleRevokePermissionsReq request);

    @POST("/v1.0/iot-03/idaas/user-role")
    @Override
    Boolean grantRoleToUser(@Body UserGrantRoleReq req);

    @POST("/v1.0/iot-03/idaas/user-batch-role")
    @Override
    Boolean setRolesToUser(@Body UserGrantRolesReq req);

    @DELETE("/v1.0/iot-03/idaas/user-role")
    @Override
    Boolean revokeRoleFromUser(@Query("space_id")String spaceId,
                               @Query("role_code") String roleCode,
                               @Query("uid") String uid);

    @POST("/v1.0/iot-03/idaas/delete-user-batch-role")
    @Override
    Boolean revokeRolesFromUser(@Body UserRevokeRolesReq req);
}
