package com.tuya.iot.server.ability.notice.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Query;
import com.tuya.iot.server.ability.notice.ability.SmsAbility;
import com.tuya.iot.server.ability.notice.model.*;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/13
 **/
public interface SmsConnector extends SmsAbility {

    @Override
    @POST("/v1.0/iot-03/messages/sms/actions/push")
    PushResult push(@Body SmsPushReq smsPushRequest);

    @Override
    @POST("/v1.0/iot-03/msg-templates/sms")
    NoticeTemplateApplyRes applyTemplate(@Body SmsTemplateReq smsTemplateRequest);

    @Override
    @GET("/v1.0/iot-03/msg-templates/sms")
    NoticeTemplateListResult getTemplateList(@Query("page_no") int pageNo,
                                             @Query("page_size") int pageSize,
                                             @Query("sort") int sort);
}
