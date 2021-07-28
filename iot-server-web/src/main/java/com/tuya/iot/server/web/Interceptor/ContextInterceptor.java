package com.tuya.iot.server.web.Interceptor;

import com.tuya.iot.server.core.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/20
 */
@Slf4j
public class ContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String language = httpServletRequest.getHeader("Accept-Language");
        if (!StringUtils.isEmpty(language)) {
            ContextUtil.setLanguage(language);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        ContextUtil.remove();
    }

}
