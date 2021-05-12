package com.tuya.iot.suite.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
public class MixUtil {

    public static final String MAIL_PATTERN_STR = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final Pattern MAIL_PATTERN = Pattern.compile(MAIL_PATTERN_STR);

    public static final String COUNTRY_CN = "CN";

    public static final String COUNTRY_EN = "EN";

    /**
     * 邮箱格式检验
     * @param mail
     * @return
     */
    public static boolean mailFormatValidate(String mail) {
        return MAIL_PATTERN.matcher(mail).matches();
    }

}
