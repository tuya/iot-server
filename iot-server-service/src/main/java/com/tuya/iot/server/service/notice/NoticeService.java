package com.tuya.iot.server.service.notice;

import com.tuya.iot.server.service.notice.model.MailPushBo;
import com.tuya.iot.server.service.notice.model.SmsPushBo;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
public interface NoticeService {

    boolean push(MailPushBo mailPushBo);

    boolean push(SmsPushBo smsPushBo);
}
