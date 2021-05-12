package com.tuya.iot.suite.ability.notice.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.iot.suite.ability.notice.ability.VoiceAbility;
import com.tuya.iot.suite.ability.notice.model.BasePushResult;
import com.tuya.iot.suite.ability.notice.model.NoticeTemplateApplyRes;
import com.tuya.iot.suite.ability.notice.model.VoicePushReq;
import com.tuya.iot.suite.ability.notice.model.VoiceTemplateReq;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/14
 */
public interface VoiceConnector extends VoiceAbility {

    @Override
    @POST("/v1.0/iot-03/messages/voices/actions/push")
    BasePushResult push(@Body VoicePushReq request);

    @Override
    @POST("/v1.0/iot-03/msg-templates/voices")
    NoticeTemplateApplyRes applyTemplate(@Body VoiceTemplateReq voiceTemplateRequest);

}
