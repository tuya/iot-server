package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.core.util.LibPhoneNumberUtil;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.model.criteria.UserCriteria;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
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
    @PostMapping(value = "/login2")
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

    @PostMapping("/login")
    public Object login(String username,String password) {
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //ShiroUtils.ensureUserIsLoggedOut();
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            UserToken token = new UserToken();
            token.setUserId("userId");
            token.setToken("token123");
            token.setNickName("benguan.zhou");
            token.setRoleType(1);
            SecurityUtils.getSubject().getSession().setAttribute("token", token);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }
        return "hello";
    }
}
