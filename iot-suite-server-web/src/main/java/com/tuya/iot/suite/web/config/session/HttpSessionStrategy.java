package com.tuya.iot.suite.web.config.session;

import org.springframework.session.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会话策略, 比如会话的标识从哪里获取
 */
public class HttpSessionStrategy implements org.springframework.session.web.http.HttpSessionStrategy {

    private String name;

    public HttpSessionStrategy() {
        this("token");
    }

    public HttpSessionStrategy(String name) {
        this.name = name;
    }

    @Override
    public String getRequestedSessionId(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        /*// 从header 中获取
        String token = request.getHeader(name);
        if (token != null) return token;

        // 从请求参数中获取
        token = request.getParameter(name);
        if (token != null) return token;*/

        return null;
    }

    @Override
    public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader(this.name, session.getId());
        Cookie cookie = new Cookie(this.name, session.getId());
        cookie.setPath("/");
//        cookie.setMaxAge(timeout);
        response.addCookie(cookie);
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader(this.name, "");
        Cookie cookie = new Cookie(this.name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }

    public void setName(String name) {
        this.name = name;
    }
}
