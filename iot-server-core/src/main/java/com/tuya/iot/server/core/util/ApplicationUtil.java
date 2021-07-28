package com.tuya.iot.server.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/19
 */
@Component
public class ApplicationUtil implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext context;

    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtil.context = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        ApplicationUtil.environment = environment;
    }

    public static String getProperty(String key) {
        return ApplicationUtil.environment.getProperty(key);
    }

    public static <T> T getProperty(String key, Class<T> t) {
        return ApplicationUtil.environment.getProperty(key, t);
    }
    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
