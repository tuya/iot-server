package com.tuya.iot.suite.ability.idaas.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.DELETE;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.PUT;
import com.tuya.connector.api.annotations.Path;
import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility;
import com.tuya.iot.suite.ability.idaas.model.*;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface IdaasUserConnector extends IdaasUserAbility {

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/users")
    @Override
    Boolean createUser(@Path("space_id")Long spaceId, @Body IdaasUserCreateReq request);

    @PUT("/v1.0/iot-03/idaas/spaces/{space_id}/users/{uid}")
    @Override
    Boolean updateUser(@Path("space_id")Long spaceId,
                       @Path("uid")String uid,
                       @Body IdaasUserUpdateReq req);

    @DELETE("/v1.0/iot-03/idaas/spaces/{space_id}/users/{uid}")
    @Override
    Boolean deleteUser(@Path("space_id")Long spaceId,
                       @Path("uid")String uid);

    @GET("/v1.0/iot-03/idaas/spaces/{space_id}/users/{uid}")
    @Override
    IdaasUser getUserByUid(@Path("space_id")Long spaceId,
                           @Path("uid")String uid);

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/page-user")
    @Override
    IdaasPageResult<IdaasUser> queryUserPage(@Path("space_id")Long spaceId,
                                             @Body IdaasUserPageReq req);


}
