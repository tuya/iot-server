package com.tuya.iot.server.service.user.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tuya.iot.server.core.constant.NoticeType;
import com.tuya.iot.server.core.exception.ServiceLogicException;
import com.tuya.iot.server.core.util.LocalDateTimeUtil;
import com.tuya.iot.server.core.util.RandomUtil;
import com.tuya.iot.server.service.notice.NoticeService;
import com.tuya.iot.server.service.notice.model.MailPushBo;
import com.tuya.iot.server.service.notice.model.SmsPushBo;
import com.tuya.iot.server.service.notice.template.CaptchaNoticeTemplate;
import com.tuya.iot.server.service.user.CaptchaService;
import com.tuya.iot.server.service.user.model.CaptchaCache;
import com.tuya.iot.server.service.user.model.CaptchaPushBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.tuya.iot.server.core.constant.ErrorCode.CAPTCHA_ALREADY_EXITS;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    public static final String CAPTCHA_PREFIX = "iot-server:captcha";

    public static final String CAPTCHA_LIMIT_PREFIX = "iot-server:captcha:limit:";

    public static final int CAPTCHA_NUMBER = 6;


    private Cache<String, CaptchaCache> CACHE = Caffeine.newBuilder()
            .expireAfterWrite(300, TimeUnit.SECONDS)
            .maximumSize(100)
            .build();

    private Cache<String, Integer> CACHE_COUNT = Caffeine.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(100)
            .build();

    @Autowired
    private NoticeService noticeService;

    //@Autowired
    //private RedisTemplate<String, Object> redisTemplate;

    private String getCaptchaKey(String type, String unionId) {
        return CAPTCHA_PREFIX + ":" + type + ":" + unionId;
    }

    @Override
    public String generateCaptcha(String type, String unionId, long timeout) {
        String key = getCaptchaKey(type, unionId);
        String code = RandomUtil.getStringWithNumber(CAPTCHA_NUMBER);
        CaptchaCache captchaCache = new CaptchaCache(code, type, LocalDateTime.now());
        CACHE.put(key, captchaCache);
        /*if (timeout > 0) {
            redisTemplate.opsForValue().set(key, captchaCache, timeout, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(key, captchaCache);
        }*/
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
        return CACHE.getIfPresent(type);
        /*
        Object object = redisTemplate.opsForValue().get(getCaptchaKey(type, unionId));
        return Objects.isNull(object) ? null : (CaptchaCache) object;*/
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
        CACHE.invalidate(getCaptchaKey(type, unionId));
        //redisTemplate.delete(getCaptchaKey(type, unionId));
    }

    @Override
    public void captchaValidateErrorIncr(String unionId) {
        String key = CAPTCHA_LIMIT_PREFIX + unionId;
        Integer count = CACHE_COUNT.getIfPresent(key);
        if (Objects.isNull(count)) {
            count = 1;
        }else {
            count += 1;
        }
        CACHE_COUNT.put(key, count);
        /*if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().increment(key, 1);
        }else {
            redisTemplate.opsForValue().increment(key, 1);
            redisTemplate.expire(key, 24, TimeUnit.HOURS);
        }*/
    }

    @Override
    public boolean captchaValidateLimit(String unionId, int limit) {
        String key = CAPTCHA_LIMIT_PREFIX + unionId;
        Integer count = CACHE_COUNT.getIfPresent(key);
        count = count == null ? 0 : count;
        return count < limit;
       /* if (redisTemplate.hasKey(key)) {
            return (int) redisTemplate.opsForValue().get(key) < limit;
        }
        return true;*/
    }

    @Override
    public void captchaValidateErrorClear(String unionId) {
        String key = CAPTCHA_LIMIT_PREFIX + unionId;
        CACHE_COUNT.invalidate(key);
        /*if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }*/
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
