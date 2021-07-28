package com.tuya.iot.server.web.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/11
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class RequestLoggerFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("request =>{} {}", req.getMethod(), req.getRequestURI());
        filterchain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }


}