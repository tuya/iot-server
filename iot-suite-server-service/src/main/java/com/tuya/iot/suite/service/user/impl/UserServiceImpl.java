package com.tuya.iot.suite.service.user.impl;


import com.tuya.iot.suite.ability.notification.model.SmsPushRequest;
import com.tuya.iot.suite.ability.user.ability.UserAbility;
import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.ability.user.model.UserModifyRequest;
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest;
import com.tuya.iot.suite.ability.user.model.UserToken;
import com.tuya.iot.suite.core.constant.NotificationType;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description 用户实现类$
 * @Author bade
 * @Since 2021/3/15 8:46 下午
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAbility userAbility;

    //@Autowired
    //private SmsService smsService;

    //@Autowired
    //private MailService mailService;

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

    public boolean getCaptcha() {
        return false;
    }


    @Override
    public boolean captchaPush(CaptchaPushBo bo) {
        NotificationType type = NotificationType.getType(bo.getType());
        if (Objects.isNull(type)) {
            log.info("captcha type incorrect!, type:[{}]", bo.getType());
            return false;
        }
        switch (type) {
            case SMS :
                SmsPushRequest smsPushRequest = new SmsPushRequest();
                smsPushRequest.setCountry_code(bo.getCountry_code());
                smsPushRequest.setPhone(bo.getPhone());
                smsPushRequest.setTemplate_id(type.getTemplate().getTemplateId());
                //smsPushRequest.setTemplatep_aram(type.getTemplate().getTemplate_param());
                //smsService.push();
                break;
            case MAIL:

                break;
            default:
                log.info("captcha push type not support!, type:[{}]", bo.getType());
                return false;
        }
        return false;
    }

    @Override
    public MobileCountries selectMobileCountries() {
        return userAbility.selectMobileCountries();
    }
}

