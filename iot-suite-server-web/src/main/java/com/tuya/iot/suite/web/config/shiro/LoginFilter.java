package com.tuya.iot.suite.web.config.shiro;

import com.alibaba.fastjson.JSON;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.core.util.ContextUtil;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.util.WebUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author benguan.zhou
 */
@Slf4j
public class LoginFilter extends AccessControlFilter {

    @Setter
    private I18nMessage i18nMessage;

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object arg2) throws Exception {
        // 已登录或者是登录请求则允许访问
        HttpServletRequest request = (HttpServletRequest) req;
        if(log.isDebugEnabled()){
            log.debug(request.getRequestURI());
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object token = session.getAttribute("token");
            //判断session中是否有用户数据，如果有，则返回true，继续向下执行
            if (token != null) {
                ContextUtil.setUserToken((UserToken) token);
                return true;
            }
        }
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            return true;
        }
        if(isLoginRequest(req, resp)){
            //用于登录成功后跳转到登录前想访问的页面
            this.saveRequest(req);
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletResponse response = (HttpServletResponse) resp;
        //HttpServletRequest request = (HttpServletRequest) req;
        String body = JSON.toJSONString(Response.buildFailure(ErrorCode.NO_LOGIN.getCode(),
                i18nMessage.getMessage(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg())));
        WebUtils.sendResponse(response, body);
        return false;
        /*if(WebUtils.isAjax(request)){
            WebUtils.sendAjaxRedirect(request, response, getLoginUrl());
        }else{
            this.saveRequestAndRedirectToLogin(req, resp);//在禁用cookie的情况下，该方法会自动在重定向的地址后面添加JSESSIONID（如贵重定向到当前域）。
        }
        return false;*/
    }

}
