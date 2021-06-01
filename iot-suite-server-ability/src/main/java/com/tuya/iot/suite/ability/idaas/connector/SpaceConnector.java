package com.tuya.iot.suite.ability.idaas.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq;
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyResp;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface SpaceConnector extends SpaceAbility {

    @POST("/v1.0/iot-03/idaas/spaces")
    @Override
    SpaceApplyResp applySpace(@Body SpaceApplyReq spaceApplyRequest);

}