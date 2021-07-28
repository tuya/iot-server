package com.tuya.iot.server.ability.notice.model;

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
public class NoticeTemplateApplyRes {

    private String template_id;
}
