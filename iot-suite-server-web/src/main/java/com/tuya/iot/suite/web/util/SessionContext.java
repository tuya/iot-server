package com.tuya.iot.suite.web.util;

import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.web.config.HttpRequestUtils;

import javax.servlet.http.HttpSession;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public abstract class SessionContext {
    public static final String USER_TOKEN_ATTR_NAME = "token";
    public static HttpSession getSession(){
        return HttpRequestUtils.getHttpSession();
    }
    public static UserToken getUserToken(){
        HttpSession session = HttpRequestUtils.getHttpSession();
        if(session == null){
            return null;
        }
        return (UserToken) session.getAttribute(USER_TOKEN_ATTR_NAME);
    }
    public static void setUserToken(UserToken userToken){
        HttpRequestUtils.getHttpSession().setAttribute(USER_TOKEN_ATTR_NAME,userToken);
    }
}
