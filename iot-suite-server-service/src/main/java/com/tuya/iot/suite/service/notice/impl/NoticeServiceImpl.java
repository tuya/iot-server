package com.tuya.iot.suite.service.notice.impl;

import com.tuya.iot.suite.ability.notice.ability.MailAbility;
import com.tuya.iot.suite.ability.notice.ability.SmsAbility;
import com.tuya.iot.suite.ability.notice.model.MailPushReq;
import com.tuya.iot.suite.ability.notice.model.PushResult;
import com.tuya.iot.suite.ability.notice.model.SmsPushReq;
import com.tuya.iot.suite.service.notice.NoticeService;
import com.tuya.iot.suite.service.notice.model.MailPushBo;
import com.tuya.iot.suite.service.notice.model.SmsPushBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private MailAbility mailAbility;

    @Autowired
    private SmsAbility smsAbility;

    @Override
    public boolean push(MailPushBo mailPushBo) {
        MailPushReq mailPushReq = new MailPushReq();
        mailPushReq.setTo_address(mailPushBo.getToAddress());
        mailPushReq.setTemplate_id(mailPushBo.getTemplateId());
        mailPushReq.setTemplate_param(mailPushBo.getTemplateParam());
        mailPushReq.setReply_to_address(mailPushBo.getReplyToAddress());
        PushResult push = mailAbility.push(mailPushReq);
        return push.isSend_status();
    }

    @Override
    public boolean push(SmsPushBo smsPushBo) {
        SmsPushReq smsPushReq = new SmsPushReq();
        smsPushReq.setTemplate_id(smsPushBo.getTemplateId());
        smsPushReq.setTemplate_param(smsPushBo.getTemplateParam());
        smsPushReq.setCountry_code(smsPushBo.getCountryCode());
        smsPushReq.setPhone(smsPushBo.getPhone());
        PushResult push = smsAbility.push(smsPushReq);
        return push.isSend_status();
    }
}
