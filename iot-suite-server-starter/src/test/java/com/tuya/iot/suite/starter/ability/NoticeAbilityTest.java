package com.tuya.iot.suite.starter.ability;

import com.alibaba.fastjson.JSONObject;
import com.tuya.iot.suite.ability.notice.ability.MailAbility;
import com.tuya.iot.suite.ability.notice.ability.SmsAbility;
import com.tuya.iot.suite.ability.notice.model.*;
import com.tuya.iot.suite.starter.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
public class NoticeAbilityTest extends BaseTest {

    @Autowired
    private MailAbility mailAbility;

    @Autowired
    private SmsAbility smsAbility;

    @Test
    public void MailTemplateList() {
        NoticeTemplateListResult templateList = mailAbility.getTemplateList(1, 10, 0);
        System.out.println(JSONObject.toJSONString(templateList));
    }

    @Test
    public void mailTemplateApply() {
        MailTemplateReq req = new MailTemplateReq();
        req.setName("邮箱验证码");
        req.setSender_name("tuya");
        req.setTitle("密码重置");
        req.setContent("您正在密码重置，验证码为：${code}，5分钟内有效！");
        req.setType(0);
        req.setRemark("当前的邮件模板应用于密码重置");

        NoticeTemplateApplyRes res = mailAbility.applyTemplate(req);
        System.out.println(res.toString());
    }

    @Test
    public void mailPushTest() {
        MailPushReq req = new MailPushReq();
        req.setTemplate_id("");
        req.setTo_address("");
        req.setTemplate_param("");
        PushResult push = mailAbility.push(req);
        System.out.println(push.toString());
    }

    @Test
    public void SmsTemplateList() {
        NoticeTemplateListResult templateList = smsAbility.getTemplateList(1, 10, 0);
        System.out.println(JSONObject.toJSONString(templateList));
    }

    @Test
    public void smsTemplateApply() {
        SmsTemplateReq req = new SmsTemplateReq();
        req.setName("密码重置验证码");
        req.setContent("您正在重置密码，验证码为：${code}，5分钟内有效！");
        req.setType(0);
        req.setRemark("密码重置验证码");

        NoticeTemplateApplyRes res = smsAbility.applyTemplate(req);
        System.out.println(res.toString());
    }

    @Test
    public void smsPushTest() {
        SmsPushReq smsPushReq = new SmsPushReq();
        smsPushReq.setTemplate_id("");
        smsPushReq.setTemplate_param("");
        smsPushReq.setCountry_code("");
        smsPushReq.setPhone("");
        PushResult push = smsAbility.push(smsPushReq);
        System.out.println(push.toString());
    }

}
