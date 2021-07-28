package com.tuya.iot.server.ability.notice.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Query;
import com.tuya.iot.server.ability.notice.ability.MailAbility;
import com.tuya.iot.server.ability.notice.model.*;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/4/13
 */
public interface MailConnector extends MailAbility {

    @Override
    @POST("/v1.0/iot-03/messages/mails/actions/push")
    PushResult push(@Body MailPushReq mailPushRequest);

    @Override
    @POST("/v1.0/iot-03/msg-templates/mails")
    NoticeTemplateApplyRes applyTemplate(@Body MailTemplateReq mailTemplateReq);

    @Override
    @GET("/v1.0/iot-03/msg-templates/mails")
    NoticeTemplateListResult getTemplateList(@Query("page_no") int pageNo,
                                             @Query("page_size") int pageSize,
                                             @Query("sort") int sort);
}
