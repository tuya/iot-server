package com.tuya.iot.suite.web.config.session;

import com.tuya.iot.suite.web.model.UserToken;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/4/27
 */
public class SessionUtil {

    /**
     * 获取用户ID
     *
     * @param session
     * @return 返回当前登录用户ID
     */
    public static String getUserId(HttpSession session) {
        UserToken userToken = (UserToken) session.getAttribute("token");
        if (!Objects.isNull(userToken)) {
            return userToken.getUserId();
        }
        return null;
    }
}
