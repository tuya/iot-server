package com.tuya.iot.suite.ability.notification.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.iot.suite.ability.notification.ability.MailAbility;
import com.tuya.iot.suite.ability.notification.model.BasePushResult;
import com.tuya.iot.suite.ability.notification.model.MailPushRequest;
import com.tuya.iot.suite.ability.notification.model.MailTemplateRequest;
import com.tuya.iot.suite.ability.notification.model.TemplatesResult;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/13
 */
public interface MailConnector extends MailAbility {

    @Override
    @POST("/v1.0/iot-03/messages/mails/actions/push")
    BasePushResult push(@Body MailPushRequest mailPushRequest);

    @Override
    @POST("/v1.0/iot-03/msg-templates/mails")
    TemplatesResult applyTemplate(MailTemplateRequest mailTemplateRequest);
}
