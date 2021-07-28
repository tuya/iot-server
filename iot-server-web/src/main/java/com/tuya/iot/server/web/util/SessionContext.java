package com.tuya.iot.server.web.util;

import com.tuya.iot.server.web.config.HttpRequestUtils;
import com.tuya.iot.server.core.model.UserToken;

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
