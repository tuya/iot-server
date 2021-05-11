package com.tuya.iot.suite.ability.notification.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.iot.suite.ability.notification.ability.SmsAbility;
import com.tuya.iot.suite.ability.notification.model.BasePushResult;
import com.tuya.iot.suite.ability.notification.model.SmsPushRequest;
import com.tuya.iot.suite.ability.notification.model.SmsTemplateRequest;
import com.tuya.iot.suite.ability.notification.model.TemplatesResult;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/13
 **/
public interface SmsConnector extends SmsAbility {

    @Override
    @POST("/v1.0/iot-03/messages/voices/actions/push")
    BasePushResult push(@Body SmsPushRequest smsPushRequest);

    @Override
    @POST("/v1.0/iot-03/msg-templates/sms")
    TemplatesResult applyTemplate(SmsTemplateRequest smsTemplateRequest);
}
