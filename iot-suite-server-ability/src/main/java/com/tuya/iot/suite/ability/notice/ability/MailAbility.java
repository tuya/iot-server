package com.tuya.iot.suite.ability.notice.ability;

import com.tuya.iot.suite.ability.notice.model.*;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/13
 */
public interface MailAbility {

    PushResult push(MailPushReq request);

    NoticeTemplateApplyRes applyTemplate(MailTemplateReq mailTemplateReq);

    NoticeTemplateListResult getTemplateList(int pageNo, int pageSize, int sort);

}
