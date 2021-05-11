package com.tuya.iot.suite.ability.notification.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/13
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailPushRequest extends BasePushRequest {

    /**
     * 收件人的地址
     */
    String to_address;

    /**
     * 邮件的回复地址
     */
    String reply_to_address;

}
