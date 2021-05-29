package com.tuya.iot.suite.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/29
 */
@Slf4j
public class ShiroUtils {
    // Logout the user fully before continuing.
    public static void ensureUserIsLoggedOut() {
        try {
            // Get the user if one is logged in.
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser == null) {
                return;
            }

            // Log the user out and kill their session if possible.
            currentUser.logout();
            Session session = currentUser.getSession(false);
            if (session == null) {
                return;
            }

            session.stop();
        } catch (UnknownSessionException e) {
            // Ignore all errors, as we're trying to silently
            // log the user out.
            log.info("{}",e.getMessage());
        }
    }
}
