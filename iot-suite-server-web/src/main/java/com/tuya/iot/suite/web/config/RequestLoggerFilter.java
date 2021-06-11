package com.tuya.iot.suite.web.config;

import com.tuya.connector.open.common.util.Sha256Util;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.web.util.SessionContext;
import com.tuya.iot.suite.web.util.log.Constant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

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
        log.info("requestUri=>{}", HttpRequestUtils.getHttpServletRequest().getRequestURI());
        filterchain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }


}