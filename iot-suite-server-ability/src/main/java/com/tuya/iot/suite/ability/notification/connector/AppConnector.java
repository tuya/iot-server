package com.tuya.iot.suite.ability.notification.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.PUT;
import com.tuya.iot.suite.ability.notification.ability.AppAbility;
import com.tuya.iot.suite.ability.notification.model.AppPushRequest;
import com.tuya.iot.suite.ability.notification.model.AppTemplateRequest;
import com.tuya.iot.suite.ability.notification.model.BasePushResult;
import com.tuya.iot.suite.ability.notification.model.TemplatesResult;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/13
 **/
public interface AppConnector extends AppAbility {

    @Override
    @PUT("/v1.0/iot-03/messages/app-notifications/actions/push")
    BasePushResult push(@Body AppPushRequest appPushRequest);

    @Override
    @POST("/v1.0/iot-03/msg-templates/voices")
    TemplatesResult applyTemplate(AppTemplateRequest appTemplateRequest);
}
