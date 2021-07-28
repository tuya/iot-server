package com.tuya.iot.server.core.util;

import java.util.regex.Pattern;

/***
 * @author benguan.zhou
 */
public abstract class UserNameUtil {
    private static Pattern emailPattern = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private UserNameUtil(){

    }
    public static boolean isEmail(String userName){
        if(userName==null){
            return false;
        }
        return userName.contains("@");
    }
    public static boolean isRealEmail(String userName){
        if(userName==null){
            return false;
        }
        return emailPattern.matcher(userName).matches();
    }

}
