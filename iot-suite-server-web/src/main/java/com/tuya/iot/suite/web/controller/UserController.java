package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.core.util.ContextUtil;
import com.tuya.iot.suite.core.util.LibPhoneNumberUtil;
import com.tuya.iot.suite.core.util.MixUtil;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.model.ResetPasswordReq;
import com.tuya.iot.suite.web.model.criteria.UserCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.tuya.iot.suite.core.constant.ErrorCode.*;


/**
 * @author bade
 */
@RestController
@Slf4j
@Api(value = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private I18nMessage i18nMessage;

    /**
     * 账号密码登录
     *
     * @param criteria 参数
     * @return
     */
    @ApiOperation(value = "用户登录")
    @SneakyThrows
    @PostMapping(value = "/login")
    public Response login(@RequestBody UserCriteria criteria) {
        //判断是何种登录方式
        String username = criteria.getUser_name();
        String password = criteria.getLogin_password();
        if (StringUtils.isEmpty(criteria.getUser_name())) {
            //用户名为空，采用手机登录
            //校验手机号码合法性
            if (!LibPhoneNumberUtil.doValid(criteria.getTelephone(), criteria.getCountry_code())) {
                log.info("telephone format error! =>{}{}", criteria.getCountry_code(), criteria.getTelephone());
                return Response.buildFailure(TELEPHONE_FORMAT_ERROR.getCode(),
                        i18nMessage.getMessage(TELEPHONE_FORMAT_ERROR.getCode(), TELEPHONE_FORMAT_ERROR.getMsg()));
            }
            username = criteria.getTelephone();
        }
        String userId = userService.login(username, password).getUid();
        UserToken userToken = new UserToken(userId, username, session.getId(), 1);
        session.setAttribute("token", userToken);
        return new Response(userToken);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "用户登出")
    @PostMapping(value = "/logout")
    public Response<Boolean> logout() {
        session.invalidate();
        return Response.buildSuccess(true);
    }

    /**
     * 使用会话中的信息
     *
     * @return
     */
    @ApiOperation(value = "修改密码")
    @SneakyThrows
    @PutMapping(value = "/user/password")
    public Response modifyLoginPassword(@RequestBody UserCriteria criteria) {
        String userName = criteria.getUser_name();
        String currentPassword = criteria.getCurrent_password();
        String newPassword = criteria.getNew_password();
        if (!userName.equals(ContextUtil.getNickName())) {
            return Response.buildFailure(USER_NOT_EXIST);
        }
        Boolean modifyLoginPassword = userService.modifyLoginPassword(ContextUtil.getUserId(), currentPassword, newPassword);
        return modifyLoginPassword ? Response.buildSuccess(true) :
                Response.buildFailure(USER_NOT_EXIST.getCode(), i18nMessage.getMessage(USER_NOT_EXIST.getCode(), USER_NOT_EXIST.getMsg()));
    }

    @ApiOperation(value = "获取密码重置验证码")
    @PostMapping(value = "/user/password/reset/captcha")
    public Response<Boolean> restPasswordCaptcha(@RequestBody ResetPasswordReq req) {
        resetPasswordCheck(req);
        CaptchaPushBo captchaPushBo = new CaptchaPushBo();
        captchaPushBo.setLanguage(req.getLanguage());
        captchaPushBo.setCountryCode(req.getCountryCode());
        captchaPushBo.setPhone(req.getPhone());
        captchaPushBo.setMail(req.getMail());
        return Response.buildSuccess(userService.sendRestPasswordCaptcha(captchaPushBo));
    }

    @ApiOperation(value = "用户密码重置")
    @PostMapping(value = "/user/password/reset")
    public Response<Boolean> resetPassword(@RequestBody @Valid ResetPasswordReq req) {
        // 参数校验
        resetPasswordCheck(req);
        if (StringUtils.isEmpty(req.getCode()) || StringUtils.isEmpty(req.getNewPassword())) {
            return Response.buildFailure(PARAM_ERROR);
        }
        ResetPasswordBo resetPasswordBo = new ResetPasswordBo();
        resetPasswordBo.setLanguage(req.getLanguage());
        resetPasswordBo.setMail(req.getMail());
        resetPasswordBo.setCountryCode(req.getCountryCode());
        resetPasswordBo.setPhone(req.getPhone());
        resetPasswordBo.setPassword(req.getNewPassword());
        resetPasswordBo.setCode(req.getCode());
        return Response.buildSuccess(userService.resetPassword(resetPasswordBo));
    }

    @ApiOperation(value = "获取手机号国际区号")
    @GetMapping(value = "/mobile/countries")
    public Response<MobileCountries> selectMobileCountries() {
        return new Response<>(userService.selectMobileCountries());
    }

    /**
     * 重置密码参数校验
     *
     * @param req
     */
    public void resetPasswordCheck(ResetPasswordReq req) {
        if (StringUtils.isEmpty(req.getMail())
                && StringUtils.isEmpty(req.getCountryCode())
                && StringUtils.isEmpty(req.getPhone())) {
            throw new ServiceLogicException(PARAM_ERROR);
        }
        // 验证手机格式
        if (!StringUtils.isEmpty(req.getCountryCode())
                && !StringUtils.isEmpty(req.getPhone())) {
            if (!LibPhoneNumberUtil.doValid(req.getPhone(), req.getCountryCode())) {
                log.info("telephone format error! =>{}{}", req.getCountryCode(), req.getPhone());
                throw new ServiceLogicException(TELEPHONE_FORMAT_ERROR);
            }
        }
        // 验证邮箱格式
        if (!StringUtils.isEmpty(req.getMail()) && !MixUtil.mailFormatValidate(req.getMail())) {
            log.info("mail format error! => [{}]", req.getMail());
            throw new ServiceLogicException(PARAM_ERROR);
        }
    }

}
