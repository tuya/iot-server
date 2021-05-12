package com.tuya.iot.suite.ability.notice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Description:
 * @author: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailTemplateReq {
    String name;
    String title;
    String sender_name;
    String content;
    int type;
    String remark;
}
