package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.LibPhoneNumberUtil;
import com.tuya.iot.suite.web.model.request.login.LoginReq;
import com.tuya.iot.suite.web.util.Responses;
import com.tuya.iot.suite.web.util.SessionContext;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author benguan
 */
@RestController
@Slf4j
public class LoginController {

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
                return Responses.buildFailure(ErrorCode.TELEPHONE_FORMAT_ERROR);
            }
            username = req.getTelephone();
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        return new Response(SessionContext.getUserToken());
    }
}
