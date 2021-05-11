package com.tuya.iot.suite.web.i18n;

import com.tuya.iot.suite.web.config.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/24
 **/
@Component
@Slf4j
public class I18nMessage {

    @Autowired
    private MessageSource messageSource;

    /**
     * return the resolved the message
     *
     * @param code a exception code
     * @return the resolved message if the lookup was successful, otherwise the default message("")
     */
    public String getMessage(String code) {
        return getMessage(code, null);

    }


    /**
     * @param code       exception code
     * @param defaultMsg the default message
     * @return the resolved message if the lookup was successful,otherwise the default message passed as a parameter
     */
    public String getMessage(String code, String defaultMsg) {
        return messageSource.getMessage(code, null, defaultMsg, getLocale());
    }

    /**
     * @param code       exception code
     * @param args       a default message to return if the lookup fails
     * @param defaultMsg the default message
     * @return the resolved message if the lookup was successful,otherwise the default message passed as a parameter
     */
    public String getMessage(String code, Object[] args, String defaultMsg) {
        return messageSource.getMessage(code, args, defaultMsg, getLocale());
    }

    /**
     * get the locale from HttpRequestUtils
     *
     * @return
     */
    private Locale getLocale() {
        Locale locale = HttpRequestUtils.getLanguageLocaleByHttpHeader();
        log.info("The locale[country:{},language:{}]", locale.getCountry(), locale.getLanguage());
        return locale;
    }

}
