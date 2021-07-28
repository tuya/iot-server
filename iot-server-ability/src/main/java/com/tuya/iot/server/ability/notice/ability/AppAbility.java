package com.tuya.iot.server.ability.notice.ability;

import com.tuya.iot.server.ability.notice.model.AppPushReq;
import com.tuya.iot.server.ability.notice.model.AppTemplateReq;
import com.tuya.iot.server.ability.notice.model.BasePushResult;
import com.tuya.iot.server.ability.notice.model.NoticeTemplateApplyRes;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
public interface AppAbility {
    BasePushResult push(AppPushReq appPushReq);

    NoticeTemplateApplyRes applyTemplate(AppTemplateReq appTemplateReq);
}
