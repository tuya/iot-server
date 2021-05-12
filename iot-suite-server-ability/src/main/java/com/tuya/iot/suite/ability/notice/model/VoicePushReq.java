package com.tuya.iot.suite.ability.notice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/4/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoicePushReq extends BasePushReq {

    /**
     * 国家码
     */
    String country_code;

    /**
     * 接收语音通知的手机号码
     */
    String phone;

}
