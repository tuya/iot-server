package com.tuya.iot.suite.web.Interceptor;

import com.tuya.iot.suite.web.i18n.I18nMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig {

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

}
