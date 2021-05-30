package com.tuya.iot.suite.web.config;

import com.tuya.connector.api.error.ErrorInfo;
import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.connector.api.exceptions.ConnectorResultException;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.regex.Pattern;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/17
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern errMsgFixPattern = Pattern.compile(",traceId=[0-9a-zA-Z]+");

    @Autowired
    private I18nMessage i18NMessage;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("全局拦截Exception异常:", e);
        log.error(e.getBindingResult().getFieldError().getDefaultMessage());
        return Response
                .buildFailure(ErrorCode.PARAM_ERROR.getCode(),
                        getI18nMessageByCode(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg()));
    }

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
        String errMsg = fixErrMsgIfNeed(errorInfo.getErrorMsg());
        return Response.buildFailure(errorInfo.getErrorCode(), getI18nMessageByCode(errorInfo.getErrorCode(), errMsg));
    }

    /**
     * 例如云端返回：旧密码不正确,traceId=Auto0594a1abb02041e6a749c0afdacf7755
     * 我们返回的时候，要去掉后面的",traceId=xxxx
     */
    private String fixErrMsgIfNeed(String errorMsg) {
        if(StringUtils.hasText(errorMsg)){
            return errMsgFixPattern.matcher(errorMsg).replaceFirst("");
        }
        return errorMsg;
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


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Response handleException(UnauthorizedException e) {
        log.error("全局拦截Exception异常:", e);
        return Response.buildFailure(ErrorCode.USER_NOT_AUTH.getCode(),
                getI18nMessageByCode(ErrorCode.USER_NOT_AUTH.getCode(), ErrorCode.USER_NOT_AUTH.getMsg()));
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
