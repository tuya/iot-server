package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.LibPhoneNumberUtil;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.model.UserToken;
import com.tuya.iot.suite.web.model.criteria.UserCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.tuya.iot.suite.core.constant.ErrorCode.TELEPHONE_FORMAT_ERROR;
import static com.tuya.iot.suite.core.constant.ErrorCode.USER_NOT_EXIST;


/**
 * @author bade
 */
@RestController
@Slf4j
@Api(description = "用户管理")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
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
            if (!LibPhoneNumberUtil.doValid(criteria.getTelephone(),criteria.getCountry_code() )) {
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
    public Response logout() {
        session.invalidate();
        return new Response(true);
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
        UserToken userToken = (UserToken) session.getAttribute("token");
        String userName = criteria.getUser_name();
        String currentPassword = criteria.getCurrent_password();
        String newPassword = criteria.getNew_password();
        if (!userName.equals(userToken.getNickName())) {
            return Response.buildFailure(USER_NOT_EXIST);
        }
        Boolean modifyLoginPassword = userService.modifyLoginPassword(userToken.getUserId(), currentPassword, newPassword);
        return modifyLoginPassword ? Response.buildSuccess(true) :
                Response.buildFailure(USER_NOT_EXIST.getCode(), i18nMessage.getMessage(USER_NOT_EXIST.getCode(), USER_NOT_EXIST.getMsg()));
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/captcha")
    public Response verificationCode() {
        return new Response(true);
    }

    @ApiOperation(value = "用户密码重置")
    @PutMapping(value = "/user/password/reset")
    public Response resetPassword() {
        return new Response(true);
    }

    @ApiOperation(value = "获取手机号国际区号")
    @GetMapping(value = "/mobile/countries")
    public Response<MobileCountries> selectMobileCountries() {
        return new Response<>(userService.selectMobileCountries());
    }

}
