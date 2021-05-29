package com.tuya.iot.suite.web.Interceptor;

import com.tuya.iot.suite.core.util.ContextUtil;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
@Order(1)
@Configuration
public class ContextInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private I18nMessage i18nMessage;

    @Bean
    WebMvcConfigurer createWebMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new ContextInterceptor())
                        .excludePathPatterns("/hc.do")
                        .excludePathPatterns("/v2/api-docs")
                        .addPathPatterns("/**");
                /*registry.addInterceptor(new LoginInterceptor(i18nMessage))
                        //排除拦截
                        .excludePathPatterns("/login")
                        .excludePathPatterns("/mobile/countries")
                        .excludePathPatterns("/hc.do")
                        .excludePathPatterns("/v2/api-docs")
                        .excludePathPatterns("/swagger-resources/**")
                        .excludePathPatterns("/configuration/ui")
                        .excludePathPatterns("/configuration/security")
                        .excludePathPatterns("/user/password/reset/captcha")
                        .excludePathPatterns("/user/password/reset")
                        //拦截路径
                        .addPathPatterns("/**");*/
            }
        };
    }

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
