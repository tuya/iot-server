package com.tuya.iot.suite.ability.notification.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.iot.suite.ability.notification.ability.VoiceAbility;
import com.tuya.iot.suite.ability.notification.model.BasePushResult;
import com.tuya.iot.suite.ability.notification.model.TemplatesResult;
import com.tuya.iot.suite.ability.notification.model.VoicePushRequest;
import com.tuya.iot.suite.ability.notification.model.VoiceTemplateRequest;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/14
 */
public interface VoiceConnector extends VoiceAbility {

    @Override
    @POST("/v1.0/iot-03/messages/voices/actions/push")
    BasePushResult push(@Body VoicePushRequest request);

    @Override
    @POST("/v1.0/iot-03/msg-templates/voices")
    TemplatesResult applyTemplate(VoiceTemplateRequest voiceTemplateRequest);

}
