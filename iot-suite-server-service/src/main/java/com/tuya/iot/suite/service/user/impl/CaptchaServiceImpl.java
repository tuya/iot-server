package com.tuya.iot.suite.service.user.impl;

import com.tuya.iot.suite.core.constant.NoticeType;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.util.RandomUtil;
import com.tuya.iot.suite.service.notice.NoticeService;
import com.tuya.iot.suite.service.notice.model.MailPushBo;
import com.tuya.iot.suite.service.notice.model.SmsPushBo;
import com.tuya.iot.suite.service.notice.template.CaptchaNoticeTemplate;
import com.tuya.iot.suite.service.user.CaptchaService;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.tuya.iot.suite.core.constant.ErrorCode.*;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    public static final String CAPTCHA_PREFIX = "iot-suite-server:captcha:";

    public static final int CAPTCHA_NUMBER = 6;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getCaptchaKey(String type, String unionId) {
        return CAPTCHA_PREFIX + ":" + type + ":" + unionId;
    }

    @Override
    public String generateCaptcha(String type, String unionId, long timeout) {
        String key = getCaptchaKey(type, unionId);
        String code = RandomUtil.getStringWithNumber(CAPTCHA_NUMBER);
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, code, timeout, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(key, code);
        }
        return code;
    }

    @Override
    public String generateCaptchaNoRepeat(String type, String unionId, long timeout) {
        String captcha = getCaptcha(type, unionId);
        if (!StringUtils.isEmpty(captcha)) {
            throw new ServiceLogicException(CAPTCHA_ALREADY_EXITS);
        }else {
            return generateCaptcha(type, unionId, timeout);
        }
    }

    @Override
    public String getCaptcha(String type, String unionId) {
        return (String) redisTemplate.opsForValue().get(getCaptchaKey(type, unionId));
    }

    @Override
    public boolean captchaValidate(String type, String unionId, String code) {
        String captcha = getCaptcha(type, unionId);
        if (StringUtils.isEmpty(captcha)) {
            log.error("captcha does not exist! type:[{}] - unionId:[{}]", type, unionId);
            throw new ServiceLogicException(CAPTCHA_NOT_EXITS);
        }
        return captcha.equals(code);
    }


    @Override
    public void removeCaptchaFromCache(String type, String unionId) {
        redisTemplate.delete(getCaptchaKey(type, unionId));
    }

    @Override
    public boolean captchaPush(CaptchaPushBo bo, CaptchaNoticeTemplate template, String code) {
        NoticeType type = NoticeType.getType(bo.getType());
        if (Objects.isNull(type)) {
            log.error("notice type incorrect!, type:[{}]", bo.getType());
            return false;
        }
        boolean result = false;
        switch (type) {
            case SMS:
                SmsPushBo smsPushBo = new SmsPushBo();
                smsPushBo.setTemplateId(template.getTemplateId());
                smsPushBo.setTemplateParam(template.getTemplateParam());
                smsPushBo.setCountryCode(bo.getCountryCode());
                smsPushBo.setPhone(bo.getPhone());
                result = noticeService.push(smsPushBo);
                break;
            case MAIL:
                MailPushBo mailPushBo = new MailPushBo();
                mailPushBo.setTemplateId(template.getTemplateId());
                mailPushBo.setTemplateParam(template.getTemplateParam());
                mailPushBo.setToAddress(bo.getMail());
                result = noticeService.push(mailPushBo);
                break;
            case VOICE:
            case APP:
                break;
            default:
                log.error("captcha push type error!, type:[{}]", bo.getType());
        }
        return result;
    }
}
