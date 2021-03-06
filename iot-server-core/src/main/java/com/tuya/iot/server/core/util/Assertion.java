package com.tuya.iot.server.core.util;

import com.tuya.iot.server.core.constant.ErrorCode;
import com.tuya.iot.server.core.exception.ServiceLogicException;

/**
 * @author mickey
 * @date 2021年06月11日 16:10
 */
public class Assertion {

    public static void isTrue(boolean b, String message) {
        isTrue(b, ErrorCode.SYSTEM_TIP, message);
    }

    public static void isTrue(boolean b, ErrorCode code, String message) {
        if (!b) {
            throw new ServiceLogicException(code, message);
        }
    }
}

