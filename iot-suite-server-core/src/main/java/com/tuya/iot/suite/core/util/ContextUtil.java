package com.tuya.iot.suite.core.util;

import com.tuya.iot.suite.core.model.UserToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/20
 */
public class ContextUtil {

    private static final ThreadLocal<Map<String, Object>> MAP = new InheritableThreadLocal<>();

    private static final String KEY_TOKEN = "token";

    private static final String KEY_LANGUAGE = "language";


    private static Map<String, Object> getLocalMap() {
        Map<String, Object> value = MAP.get();
        if (Objects.isNull(value)) {
            value = new HashMap<>(8);
            MAP.set(value);
        }
        return value;
    }

    public static void setUserToken(UserToken userToken) {
        Map<String, Object> value = getLocalMap();
        value.put(KEY_TOKEN, userToken);
        MAP.set(value);
    }

    public static UserToken getUserToken() {
        Object o = getLocalMap().get(KEY_TOKEN);
        if (Objects.nonNull(o)) {
            return (UserToken) o;
        }
        return null;
    }

    public static String getUserId() {
        UserToken userToken = getUserToken();
        if (Objects.nonNull(userToken)) {
            return userToken.getUserId();
        }
        return null;
    }

    public static String getNickName() {
        UserToken userToken = getUserToken();
        if (Objects.nonNull(userToken)) {
            return userToken.getNickName();
        }
        return null;
    }

    public static void setLanguage(String language) {
        getLocalMap().put(KEY_LANGUAGE, language);
    }

    public static String getLanguage() {
        Object o = getLocalMap().get(KEY_LANGUAGE);
        if (Objects.nonNull(o)) {
            return (String) o;
        }
        return null;
    }

    public static void remove() {
        MAP.remove();
    }

}
