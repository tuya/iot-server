package com.tuya.iot.suite.ability.notice.ability;


import com.tuya.iot.suite.ability.notice.model.*;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/13
 **/
public interface SmsAbility {

    PushResult push(SmsPushReq smsPushRequest);

    NoticeTemplateApplyRes applyTemplate(SmsTemplateReq smsTemplateRequest);

    NoticeTemplateListResult getTemplateList(int pageNo, int pageSize, int sort);
}
