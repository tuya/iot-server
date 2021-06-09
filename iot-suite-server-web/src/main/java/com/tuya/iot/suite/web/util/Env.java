package com.tuya.iot.suite.web.util;

import com.tuya.connector.open.common.constant.TuyaRegion;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/09
 */
public class Env {
    public static final String DAILY_CN = "https://openapi-daily.tuya-inc.cn";
    public static final String PRE_CN = "https://openapi-cn.wgine.com";
    @SneakyThrows
    public static void use(String apiUrl){
        if(!StringUtils.hasText(apiUrl)){
            apiUrl = TuyaRegion.CN.getApiUrl();
        }
        Field apiUrlField = TuyaRegion.class.getDeclaredField("apiUrl");
        apiUrlField.setAccessible(true);
        //设置环境为开发环境
        String finalApiUrl = apiUrl;
        Stream.of( TuyaRegion.values()).forEach(it-> {
            try {
                apiUrlField.set(it, finalApiUrl);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void useDailyCn(){
        use(DAILY_CN);
    }
    public static void usePreCn(){
        use(PRE_CN);
    }
}
