package com.tuya.iot.suite.ability.idaas.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.DELETE;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.PUT;
import com.tuya.connector.api.annotations.Path;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleQueryReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface RoleConnector extends RoleAbility {

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/roles")
    @Override
    Boolean createRole(@Path("space_id") Long spaceId, @Body IdaasRoleCreateReq request);

    @PUT("/v1.0/iot-03/idaas/spaces/{space_id}/roles/{role_code}")
    @Override
    Boolean updateRole(@Path("space_id") Long spaceId,
                       @Path("role_code")String roleCode,
                       @Body RoleUpdateReq request);

    @DELETE("/v1.0/iot-03/idaas/spaces/{space_id}/roles/{role_code}")
    @Override
    Boolean deleteRole(@Path("space_id") Long spaceId,
                       @Path("role_code")String roleCode);

    @GET("/v1.0/iot-03/idaas/spaces/{space_id}/roles/{role_code}")
    @Override
    IdaasRole getRole(Long spaceId, String roleCode);

    @POST("/v1.0/iot-03/idaas/get-batch-role")
    @Override
    List<IdaasRole> queryRolesByCodes(@Body RoleQueryReq request);

    @GET("/v1.0/iot-03/idaas/spaces/{space_id}/users/{uid}/roles")
    @Override
    List<IdaasRole> queryRolesByUser(@Path("space_id")Long spaceId,
                                     @Path("uid")String uid);
}
