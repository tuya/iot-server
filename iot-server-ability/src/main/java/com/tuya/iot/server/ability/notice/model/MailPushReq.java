package com.tuya.iot.server.ability.notice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/4/13
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailPushReq extends BasePushReq {

    /**
     * 收件人的地址
     */
    String to_address;

    /**
     * 邮件的回复地址
     */
    String reply_to_address;

}
