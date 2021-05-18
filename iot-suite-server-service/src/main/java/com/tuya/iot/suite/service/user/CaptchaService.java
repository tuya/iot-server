package com.tuya.iot.suite.service.user;

import com.tuya.iot.suite.service.notice.template.CaptchaNoticeTemplate;
import com.tuya.iot.suite.service.user.model.CaptchaCache;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
public interface CaptchaService {

    /**
     * generate captcha
     *
     * @param bizType
     * @param userId
     * @param timeout
     * @return
     */
    String generateCaptcha(String bizType, String userId, long timeout);

    /**
     * generate captcha in permitTIme
     *
     * @param type
     * @param unionId
     * @param timeout
     * @param permitTime
     * @return
     */
    String generateCaptchaInPermitTime(String type, String unionId, long timeout, long permitTime);

    /**
     *
     *
     * @param type
     * @param unionId
     * @param timeout
     * @return
     */
    String generateCaptchaNoRepeat(String type, String unionId, long timeout);

    /**
     * 获取验证码
     *
     * @param type
     * @param unionId
     * @return
     */
    CaptchaCache getCaptcha(String type, String unionId);

    /**
     * 验证码校验
     *
     * @param type
     * @param unionId
     * @param code
     * @return
     */
    boolean captchaValidate(String type, String unionId, String code);

    /**
     * 移除验证码
     *
     * @param type
     * @param unionId
     */
    void removeCaptchaFromCache(String type, String unionId);

    /**
     * send captcha
     *
     * @param bo
     * @param template
     * @param code
     * @return
     */
    boolean captchaPush(CaptchaPushBo bo, CaptchaNoticeTemplate template, String code);
}
