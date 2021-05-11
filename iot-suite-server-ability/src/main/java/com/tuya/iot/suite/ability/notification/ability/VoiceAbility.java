package com.tuya.iot.suite.ability.notification.ability;


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
public interface VoiceAbility {

    BasePushResult push(VoicePushRequest request);

    TemplatesResult applyTemplate(VoiceTemplateRequest voiceTemplateRequest);


}
