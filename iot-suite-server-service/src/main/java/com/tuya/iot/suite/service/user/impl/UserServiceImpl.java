package com.tuya.iot.suite.service.user.impl;

import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.iot.suite.ability.notice.model.ResetPasswordReq;
import com.tuya.iot.suite.ability.user.ability.UserAbility;
import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.ability.user.model.UserModifyRequest;
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest;
import com.tuya.iot.suite.ability.user.model.UserToken;
import com.tuya.iot.suite.core.constant.CaptchaType;
import com.tuya.iot.suite.core.constant.NoticeType;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.service.notice.template.CaptchaNoticeTemplate;
import com.tuya.iot.suite.service.user.CaptchaService;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.tuya.iot.suite.core.constant.ErrorCode.*;

/**
 * @Description 用户实现类
 * @Author bade
 * @Since 2021/3/15 8:46 下午
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAbility userAbility;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 修改用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public Boolean modifyLoginPassword(String userId, String oldPassword, String newPassword) {
        UserModifyRequest request = new UserModifyRequest();
        request.setOld_password(oldPassword);
        request.setNew_password(newPassword);
        Boolean success = userAbility.modifyUserPassword(userId, request);
        return success;
    }

    /**
     * 验证密码
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserToken login(String userName, String password) {
        UserRegisteredRequest request = new UserRegisteredRequest();
        request.setUsername(userName);
        request.setUser_name(userName);
        request.setPassword(password);
        return userAbility.loginUser(request);
    }

    @Override
    public MobileCountries selectMobileCountries() {
        return userAbility.selectMobileCountries();
    }

    @Override
    public boolean sendRestPasswordCaptcha(CaptchaPushBo bo) {
        NoticeType type;
        String code;
        CaptchaNoticeTemplate template;
        long timeout = 5;
        String unionId;
        if (!StringUtils.isEmpty(bo.getPhone()) && !StringUtils.isEmpty(bo.getCountryCode())) {
            unionId = bo.getCountryCode() + bo.getPhone();
            type = NoticeType.SMS;
            code = captchaService.generateCaptchaInPermitTime(CaptchaType.PASSWORD_REST, unionId, timeout * 60, 60);
            template = CaptchaNoticeTemplate.restPasswordSms(bo.getLanguage(), code, timeout);
        } else if (!StringUtils.isEmpty(bo.getMail())) {
            unionId = bo.getMail();
            type = NoticeType.MAIL;
            code = captchaService.generateCaptchaInPermitTime(CaptchaType.PASSWORD_REST, bo.getMail(), timeout * 60, 60);
            template = CaptchaNoticeTemplate.restPasswordMail(bo.getLanguage(), code, timeout);
        } else {
            log.info("captcha error! param:{}", bo.toString());
            return false;
        }
        bo.setType(type.getCode());
        // 发送失败，删除缓存的captcha
        boolean result = false;
        try {
            result = captchaService.captchaPush(bo, template, code);
        } finally {
            if (!result) {
                captchaService.removeCaptchaFromCache(CaptchaType.PASSWORD_REST, unionId);
            }
        }
        return result;
    }

    @Override
    public boolean resetPassword(ResetPasswordBo bo) {
        String unionId;
        if (!StringUtils.isEmpty(bo.getPhone()) && !StringUtils.isEmpty(bo.getCountryCode())) {
            unionId = bo.getCountryCode() + bo.getPhone();
        } else if (!StringUtils.isEmpty(bo.getMail())) {
            unionId = bo.getMail();
        } else {
            log.info("resetPassword error! param:{}", bo.toString());
            throw new ServiceLogicException(PARAM_ERROR);
        }
        // 判断是否错误验证次数上限
        if (!captchaService.captchaValidateLimit(unionId, 10)) {
            throw new ServiceLogicException(CAPTCHA_LIMIT);
        }
        boolean result = captchaService.captchaValidate(CaptchaType.PASSWORD_REST, unionId, bo.getCode());
        if (!result) {
            log.error("captcha validate failed! unionId:[{}] code:[{}]", unionId, bo.getCode());
            // 错误次数+1
            captchaService.captchaValidateErrorIncr(unionId);
            throw new ServiceLogicException(CAPTCHA_ERROR);
        }
        // 清除验证码
        captchaService.removeCaptchaFromCache(CaptchaType.PASSWORD_REST, unionId);
        // 清除错误次数限制
        captchaService.captchaValidateErrorClear(unionId);

        // 重置用户密码，手机号不需要加国家码
        if (!StringUtils.isEmpty(bo.getCountryCode()) && unionId.startsWith(bo.getCountryCode())) {
            unionId = unionId.substring(bo.getCountryCode().length());
        }
        result = userAbility.resetPassword(new ResetPasswordReq(unionId, bo.getPassword()));
        return result;
    }

    @Override
    public Boolean createUser(Long spaceId, IdaasUserCreateReq request) {
        return null;
    }

    @Override
    public Boolean updateUser(Long spaceId, String uid, IdaasUserUpdateReq req) {
        return null;
    }

    @Override
    public Boolean deleteUser(Long spaceId, String uid) {
        return null;
    }

    @Override
    public IdaasUser getUserByUid(Long spaceId, String uid) {
        return null;
    }
}

