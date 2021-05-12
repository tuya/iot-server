package com.tuya.iot.suite.ability.notice.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.PUT;
import com.tuya.iot.suite.ability.notice.ability.AppAbility;
import com.tuya.iot.suite.ability.notice.model.AppPushReq;
import com.tuya.iot.suite.ability.notice.model.AppTemplateReq;
import com.tuya.iot.suite.ability.notice.model.BasePushResult;
import com.tuya.iot.suite.ability.notice.model.NoticeTemplateApplyRes;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/13
 **/
public interface AppConnector extends AppAbility {

    @Override
    @PUT("/v1.0/iot-03/messages/app-notifications/actions/push")
    BasePushResult push(@Body AppPushReq appPushReq);

    @Override
    @POST("/v1.0/iot-03/msg-templates/voices")
    NoticeTemplateApplyRes applyTemplate(@Body AppTemplateReq appTemplateReq);
}
