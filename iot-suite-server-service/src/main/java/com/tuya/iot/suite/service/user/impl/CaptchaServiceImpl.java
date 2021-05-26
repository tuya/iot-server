package com.tuya.iot.suite.service.user.impl;

import com.tuya.iot.suite.core.constant.NoticeType;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.util.LocalDateTimeUtil;
import com.tuya.iot.suite.core.util.RandomUtil;
import com.tuya.iot.suite.service.notice.NoticeService;
import com.tuya.iot.suite.service.notice.model.MailPushBo;
import com.tuya.iot.suite.service.notice.model.SmsPushBo;
import com.tuya.iot.suite.service.notice.template.CaptchaNoticeTemplate;
import com.tuya.iot.suite.service.user.CaptchaService;
import com.tuya.iot.suite.service.user.model.CaptchaCache;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public static final String CAPTCHA_PREFIX = "iot-suite-server:captcha";

    public static final String CAPTCHA_LIMIT_PREFIX = "iot-suite-server:captcha:limit:";

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
        CaptchaCache captchaCache = new CaptchaCache(code, type, LocalDateTime.now());
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, captchaCache, timeout, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(key, captchaCache);
        }
        return code;
    }

    @Override
    public String generateCaptchaInPermitTime(String type, String unionId, long timeout, long permitTime) {
        CaptchaCache cache = getCaptcha(type, unionId);
        if (!Objects.isNull(cache)
                && LocalDateTimeUtil.calculateSecondBetween(cache.getCreateTime(), LocalDateTime.now()) < permitTime) {
            throw new ServiceLogicException(CAPTCHA_ALREADY_EXITS);
        }
        else {
            return generateCaptcha(type, unionId, timeout);
        }
    }

    @Override
    public String generateCaptchaNoRepeat(String type, String unionId, long timeout) {
        CaptchaCache cache = getCaptcha(type, unionId);
        if (!Objects.isNull(cache)) {
            throw new ServiceLogicException(CAPTCHA_ALREADY_EXITS);
        }else {
            return generateCaptcha(type, unionId, timeout);
        }
    }

    @Override
    public CaptchaCache getCaptcha(String type, String unionId) {
        Object object = redisTemplate.opsForValue().get(getCaptchaKey(type, unionId));
        return Objects.isNull(object) ? null : (CaptchaCache) object;
    }

    @Override
    public boolean captchaValidate(String type, String unionId, String code) {
        CaptchaCache cache = getCaptcha(type, unionId);
        if (Objects.isNull(cache)) {
            log.error("captcha does not exist! type:[{}] - unionId:[{}]", type, unionId);
            return false;
        }
        return code.equals(cache.getCode());
    }

    @Override
    public void removeCaptchaFromCache(String type, String unionId) {
        redisTemplate.delete(getCaptchaKey(type, unionId));
    }

    @Override
    public void captchaValidateErrorIncr(String unionId) {
        String key = CAPTCHA_LIMIT_PREFIX + unionId;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().increment(key, 1);
        }else {
            redisTemplate.opsForValue().increment(key, 1);
//            redisTemplate.expire(key, 24, TimeUnit.HOURS);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        }
    }

    @Override
    public boolean captchaValidateLimit(String unionId, int limit) {
        String key = CAPTCHA_LIMIT_PREFIX + unionId;
        if (redisTemplate.hasKey(key)) {
            return (int)(redisTemplate.opsForValue().get(key)) < limit;
        }
        return true;
    }

    @Override
    public void captchaValidateErrorClear(String unionId) {
        String key = CAPTCHA_LIMIT_PREFIX + unionId;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
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
