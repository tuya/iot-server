package com.tuya.iot.suite.web.config;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author mickey
 * @date 2021年04月24日 21:37
 */
public class HttpRequestUtils extends RequestContextHolder {


    private static String LANGUAGE_HEADER = "Accept-Language";


    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) getRequestAttributes();
        return requestAttributes.getRequest();
    }


    public static Locale getLanguageLocaleByHttpHeader() {
        HttpServletRequest request = getHttpServletRequest();

        String header = request.getHeader(LANGUAGE_HEADER);
        if (!StringUtils.isEmpty(header)) {
            Locale locale = Locale.forLanguageTag(header);
            if (!StringUtils.isEmpty(locale.getLanguage())) {
                return locale;
            }
        }
        return Locale.SIMPLIFIED_CHINESE;
    }

    public static void setLanguageHeaderName(String languageHeaderName) {
        LANGUAGE_HEADER = languageHeaderName;
    }


}
