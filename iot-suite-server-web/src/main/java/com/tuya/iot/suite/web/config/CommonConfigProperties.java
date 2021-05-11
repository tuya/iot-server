package com.tuya.iot.suite.web.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author mickey
 * @date 2021年04月24日 21:55
 */
@Getter
@Setter
@ToString
@Component
public class CommonConfigProperties implements InitializingBean {

    @Value("${in18.http.header.name:Accept-Language}")
    private String languageHeaderName;

    @Override
    public void afterPropertiesSet() throws Exception {
        HttpRequestUtils.setLanguageHeaderName(languageHeaderName);
    }
}
