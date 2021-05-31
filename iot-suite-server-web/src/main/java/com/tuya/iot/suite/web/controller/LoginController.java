package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.LibPhoneNumberUtil;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.model.LoginReq;
import com.tuya.iot.suite.web.util.SessionUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.tuya.iot.suite.core.constant.ErrorCode.TELEPHONE_FORMAT_ERROR;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private I18nMessage i18nMessage;

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "用户登出")
    @PostMapping(value = "/logout")
    public Response<Boolean> logout() {
        SecurityUtils.getSubject().logout();
        return Response.buildSuccess(true);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Object login(@RequestBody LoginReq req) {
        //判断是何种登录方式
        String username = req.getUser_name();
        String password = req.getLogin_password();
        if (StringUtils.isEmpty(req.getUser_name())) {
            //用户名为空，采用手机登录
            //校验手机号码合法性
            if (!LibPhoneNumberUtil.doValid(req.getTelephone(), req.getCountry_code())) {
                log.info("telephone format error! =>{}{}", req.getCountry_code(), req.getTelephone());
                return Response.buildFailure(TELEPHONE_FORMAT_ERROR.getCode(),
                        i18nMessage.getMessage(TELEPHONE_FORMAT_ERROR.getCode(), TELEPHONE_FORMAT_ERROR.getMsg()));
            }
            username = req.getTelephone();
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        return new Response(SessionUtils.getUserToken());
    }
}
