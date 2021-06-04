package com.tuya.iot.suite.web.util;

import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.ApplicationUtil;
import com.tuya.iot.suite.web.i18n.I18nMessage;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public class ResponseI18n {
    private static I18nMessage i18nMessage;

    private static I18nMessage getI18nMessage() {
        if (i18nMessage == null) {
            i18nMessage = ApplicationUtil.getBean(I18nMessage.class);
        }
        return i18nMessage;
    }

    public static <T> Response<T> buildSuccess(T t){
        return Response.buildSuccess(t);
    }
    public static <T> Response<T> buildFailure(ErrorCode errorCode) {
        return Response.buildFailure(errorCode.getCode(),
                getI18nMessage().getMessage(errorCode.getCode(), errorCode.getMsg()));
    }
}
