package com.tuya.iot.suite.core.exception;


import com.tuya.iot.suite.core.constant.ErrorCode;

/**
 * @author mickey
 * @date 2021年04月20日 21:01
 */
public class ServiceLogicException extends RuntimeException {

    private ErrorCode errorCode;

    private String outMsg;

    public ServiceLogicException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceLogicException(ErrorCode errorCode, String outMsg) {
        this.errorCode = errorCode;
        this.outMsg = outMsg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getOutMsg() {
        return outMsg;
    }

    public void setOutMsg(String outMsg) {
        this.outMsg = outMsg;
    }
}
