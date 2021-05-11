package com.tuya.iot.suite.web.config;

import com.tuya.connector.api.error.ErrorInfo;
import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.connector.api.exceptions.ConnectorResultException;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/17
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private I18nMessage i18NMessage;


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Response handleException(NoHandlerFoundException e) {
        log.error("全局拦截Exception异常:", e);
        return Response
                .buildFailure(ErrorCode.NOT_FOUND.getCode(),
                        getI18nMessageByCode(ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMsg()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Response handleException(HttpRequestMethodNotSupportedException e) {
        log.error("全局拦截Exception异常:", e);
        return Response.buildFailure(ErrorCode.NOT_FOUND.getCode(),
                getI18nMessageByCode(ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMsg()));
    }

    @ExceptionHandler(ServiceLogicException.class)
    @ResponseBody
    public Response handleServiceLogicException(ServiceLogicException e) {
        log.info("全局拦截ServiceLogicException提示: code={} , message={} ", e.getErrorCode().getCode(), e.getErrorCode().getMsg());
        return Response
                .buildFailure(e.getErrorCode().getCode(), getI18nMessageByCode(e.getErrorCode().getCode(), e.getErrorCode().getMsg()));
    }


    @ExceptionHandler(ConnectorResultException.class)
    @ResponseBody
    public Response handleException(ConnectorResultException e) {
        log.error("全局拦截Exception异常:", e);
        ErrorInfo errorInfo = e.getErrorInfo();
        return Response.buildFailure(errorInfo.getErrorCode(), getI18nMessageByCode(errorInfo.getErrorCode(), errorInfo.getErrorMsg()));
    }

    @ExceptionHandler(ConnectorException.class)
    @ResponseBody
    public Response handleException(ConnectorException e) {
        log.error("全局拦截Exception异常:", e);
        Throwable cause = e.getCause();
        if (cause instanceof UndeclaredThrowableException) {
            UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) cause;
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if (undeclaredThrowable instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = (InvocationTargetException) undeclaredThrowable;
                ErrorCode errorCode = ErrorCode.getByMsg(invocationTargetException.getTargetException().getMessage());
                return Response.buildFailure(errorCode.getCode(),
                        getI18nMessageByCode(errorCode.getCode(), errorCode.getMsg()));
            }
        }
        return Response.buildFailure(ErrorCode.SYSTEM_ERROR.getCode(),
                getI18nMessageByCode(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getMsg()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception e) {
        log.error("全局拦截Exception异常:", e);
        return Response.buildFailure(ErrorCode.SYSTEM_ERROR.getCode(),
                getI18nMessageByCode(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getMsg()));
    }


    /***
     *
     * @param errorCode
     * @param defaultMessage
     * @return the message
     */
    private String getI18nMessageByCode(String errorCode, String defaultMessage) {
        return i18NMessage.getMessage(errorCode, defaultMessage);
    }


}
