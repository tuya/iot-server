package com.tuya.iot.server.web.Interceptor;

import com.alibaba.fastjson.JSON;
import com.tuya.iot.server.web.i18n.I18nMessage;
import com.tuya.iot.server.web.util.SessionContext;
import com.tuya.iot.server.core.constant.ErrorCode;
import com.tuya.iot.server.core.constant.Response;
import com.tuya.iot.server.core.model.UserToken;
import com.tuya.iot.server.core.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登陆拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private I18nMessage i18nMessage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        log.info("preHandle {}...", request.getRequestURI());
        //获取session
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserToken token = SessionContext.getUserToken();
            //判断session中是否有用户数据，如果有，则返回true，继续向下执行
            if (token != null) {
                ContextUtil.setUserToken(token);
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(Response.buildFailure(ErrorCode.NO_LOGIN.getCode(),
                i18nMessage.getMessage(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg()))));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        log.debug("postHandle {}.", httpServletRequest.getRequestURI());

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
            throws Exception {
        log.debug("afterCompletion {}: exception = {}", httpServletRequest.getRequestURI(), e);

    }

    public LoginInterceptor(I18nMessage i18nMessage) {
        this.i18nMessage = i18nMessage;
    }

}
