package com.tuya.iot.suite.web.util;

import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.web.config.HttpRequestUtils;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public abstract class SessionUtils {
    public static final String USER_TOKEN_ATTR_NAME = "token";
    public static UserToken getUserToken(){
        return (UserToken) HttpRequestUtils.getHttpSession().getAttribute(USER_TOKEN_ATTR_NAME);
    }
    public static void setUserToken(UserToken userToken){
        HttpRequestUtils.getHttpSession().setAttribute(USER_TOKEN_ATTR_NAME,userToken);
    }
}
