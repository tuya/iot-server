package com.tuya.iot.suite.service.notice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailPushBo extends BasePushBo{

    String toAddress;

    String replyToAddress;
}
