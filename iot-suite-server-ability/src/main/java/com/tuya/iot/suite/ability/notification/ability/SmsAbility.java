package com.tuya.iot.suite.ability.notification.ability;


import com.tuya.iot.suite.ability.notification.model.BasePushResult;
import com.tuya.iot.suite.ability.notification.model.SmsPushRequest;
import com.tuya.iot.suite.ability.notification.model.SmsTemplateRequest;
import com.tuya.iot.suite.ability.notification.model.TemplatesResult;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/13
 **/
public interface SmsAbility {
    BasePushResult push(SmsPushRequest smsPushRequest);

    TemplatesResult applyTemplate(SmsTemplateRequest smsTemplateRequest);
}
