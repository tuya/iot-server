package com.tuya.iot.suite.ability.notification.ability;

import com.tuya.connector.api.annotations.Body;
import com.tuya.iot.suite.ability.notification.model.AppPushRequest;
import com.tuya.iot.suite.ability.notification.model.AppTemplateRequest;
import com.tuya.iot.suite.ability.notification.model.BasePushResult;
import com.tuya.iot.suite.ability.notification.model.TemplatesResult;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
public interface AppAbility {
    BasePushResult push(@Body AppPushRequest appPushRequest);

    TemplatesResult applyTemplate(AppTemplateRequest appTemplateRequest);
}
