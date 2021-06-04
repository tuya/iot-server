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
 * @date 2021/06/04
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Slf4j
public class LoggerMDCFilter implements Filter {

    private static final String PLACEHOLDER = "xx";
    private static final int HASH_LEN = 16;

    @Override
    public void destroy() {
    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) {
        String tid = generateTraceId();
        if (tid == null) {
            filterchain.doFilter(request,response);
        }else{
            try {
                MDC.put(Constant.TRACE_ID_KEY, tid);
                filterchain.doFilter(request, response);
            } finally {
                MDC.remove(Constant.TRACE_ID_KEY);
            }
        }
    }

    private String generateTraceId() {
        try {
            String uidHash = PLACEHOLDER;
            UserToken userToken = SessionContext.getUserToken();
            if (userToken != null) {
                uidHash = hash(userToken.getUserId());
            }
            String sidHash = PLACEHOLDER;
            HttpSession session = SessionContext.getSession();
            if (session != null) {
                sidHash = hash(session.getId());
            }
            String reqHash = UUID.randomUUID().toString().replaceAll("-", "");
            return uidHash + "-" + sidHash + "-" + reqHash;
        } catch (Exception e) {
            log.warn("generateTraceId error", e);
        }
        return null;
    }

    private String hash(String value) throws Exception {
        return Sha256Util.encryption(value).substring(0,HASH_LEN);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }


}