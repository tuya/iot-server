package com.tuya.iot.suite.ability.notice.ability;


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
public interface VoiceAbility {

    BasePushResult push(VoicePushReq request);

    NoticeTemplateApplyRes applyTemplate(VoiceTemplateReq voiceTemplateRequest);


}
