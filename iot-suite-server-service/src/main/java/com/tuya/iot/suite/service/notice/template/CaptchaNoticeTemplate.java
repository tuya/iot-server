package com.tuya.iot.suite.service.notice.template;

import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.util.MixUtil;
import org.springframework.util.StringUtils;

import static com.tuya.iot.suite.core.constant.ErrorCode.PARAM_ERROR;

/**
 * <p> 验证码邮件模板
 *
 * @author 哲也
 * @since 2021/5/11
 */
public class CaptchaNoticeTemplate extends NoticeTemplate {

    public CaptchaNoticeTemplate(String templateId, String templateParam) {
        super(templateId, templateParam);
    }

    public static ICaptchaNoticeTemplate getNoticeTemplate(String language) {
        if (StringUtils.isEmpty(language)) {
            throw new ServiceLogicException(PARAM_ERROR);
        }
        return MixUtil.COUNTRY_EN.equalsIgnoreCase(language) ? new CaptchaNoticeTemplateEn() : new CaptchaNoticeTemplateCn();
    }

    public static CaptchaNoticeTemplate restPasswordMail(String language, Object... param) {
        return getNoticeTemplate(language).restPasswordMail(param);
    }

    public static CaptchaNoticeTemplate restPasswordSms(String language, Object... param) {
        return getNoticeTemplate(language).restPasswordSms(param);
    }

    static class CaptchaNoticeTemplateCn implements ICaptchaNoticeTemplate {
        private static final String RESET_PASSWORD_SMS_TEMPLATE_ID = "SMS_683792742";
        private static final String RESET_PASSWORD_SMS_TEMPLATE_PARAM = "{\"code\": \"%s\",\"timeLimit\": \"%d\"}";

        private static final String RESET_PASSWORD_MAIL_TEMPLATE_ID = "MAIL_384597583";
        private static final String RESET_PASSWORD_MAIL_TEMPLATE_PARAM = "{\"code\": \"%s\",\"timeLimit\": \"%d\"}";

        @Override
        public CaptchaNoticeTemplate restPasswordSms(Object... param) {
            return new CaptchaNoticeTemplate(RESET_PASSWORD_SMS_TEMPLATE_ID, String.format(RESET_PASSWORD_SMS_TEMPLATE_PARAM, param));
        }

        @Override
        public CaptchaNoticeTemplate restPasswordMail(Object... param) {
            return new CaptchaNoticeTemplate(RESET_PASSWORD_MAIL_TEMPLATE_ID, String.format(RESET_PASSWORD_MAIL_TEMPLATE_PARAM, param));
        }
    }

    static class CaptchaNoticeTemplateEn implements ICaptchaNoticeTemplate {
        private static final String RESET_PASSWORD_SMS_TEMPLATE_ID = "SMS_647256109";
        private static final String RESET_PASSWORD_SMS_TEMPLATE_PARAM = "{\"code\": \"%s\",\"timeLimit\": \"%d\"}";

        private static final String RESET_PASSWORD_MAIL_TEMPLATE_ID = "MAIL_287988317";
        private static final String RESET_PASSWORD_MAIL_TEMPLATE_PARAM = "{\"code\": \"%s\",\"timeLimit\": \"%d\"}";

        @Override
        public CaptchaNoticeTemplate restPasswordSms(Object... param) {
            return new CaptchaNoticeTemplate(RESET_PASSWORD_SMS_TEMPLATE_ID, String.format(RESET_PASSWORD_SMS_TEMPLATE_PARAM, param));
        }

        @Override
        public CaptchaNoticeTemplate restPasswordMail(Object... param) {
            return new CaptchaNoticeTemplate(RESET_PASSWORD_MAIL_TEMPLATE_ID, String.format(RESET_PASSWORD_MAIL_TEMPLATE_PARAM, param));
        }
    }

    interface ICaptchaNoticeTemplate {
        CaptchaNoticeTemplate restPasswordSms(Object... param);
        CaptchaNoticeTemplate restPasswordMail(Object... param);
    }
}
