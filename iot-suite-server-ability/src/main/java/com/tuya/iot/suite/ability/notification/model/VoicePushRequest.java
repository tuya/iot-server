package com.tuya.iot.suite.ability.notification.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoicePushRequest extends BasePushRequest {

    /**
     * 国家码
     */
    String country_code;

    /**
     * 接收语音通知的手机号码
     */
    String phone;

}
