package com.tuya.iot.suite.web.config;

import com.tuya.connector.api.error.ErrorInfo;
import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.connector.api.exceptions.ConnectorResultException;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.util.ResponseI18n;
import com.tuya.iot.suite.web.util.log.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
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
 * @author Chyern, benguan
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
        LogUtil.info(log, e, "全局拦截MethodArgumentNotValidException异常:{}", e.getMessage());
        return ResponseI18n.buildFailure(ErrorCode.PARAM_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Response handleException(NoHandlerFoundException e) {
        LogUtil.info(log, e, "全局拦截NoHandlerFoundException异常:{}", e.getMessage());
        return ResponseI18n.buildFailure(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Response handleException(HttpRequestMethodNotSupportedException e) {
        LogUtil.info(log, e, "全局拦截HttpRequestMethodNotSupportedException异常:{}", e.getMessage());
        return ResponseI18n.buildFailure(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(ServiceLogicException.class)
    @ResponseBody
    public Response handleServiceLogicException(ServiceLogicException e) {
        LogUtil.info(log, e, "全局拦截ServiceLogicException提示: code={} , message={} ", e.getErrorCode().getCode(), e.getErrorCode().getMsg());
        String outMsg = StringUtils.isEmpty(e.getOutMsg()) ? "" : "[" + e.getOutMsg() + "]";
        return Response
                .buildFailure(e.getErrorCode().getCode(),
                        getI18nMessageByCode(e.getErrorCode().getCode(), e.getErrorCode().getMsg()) + outMsg);
    }


    @ExceptionHandler(ConnectorResultException.class)
    @ResponseBody
    public Response handleException(ConnectorResultException e) {
        log.error("全局拦截ConnectorResultException异常:", e);
        ErrorInfo errorInfo = e.getErrorInfo();
        String errMsg = fixErrMsgIfNeed(errorInfo.getErrorMsg());
        return Response.buildFailure(errorInfo.getErrorCode(), getI18nMessageByCode(errorInfo.getErrorCode(), errMsg));
    }

    /**
     * 例如云端返回：旧密码不正确,traceId=Auto0594a1abb02041e6a749c0afdacf7755
     * 我们返回的时候，要去掉后面的",traceId=xxxx
     */
    private String fixErrMsgIfNeed(String errorMsg) {
        if (StringUtils.hasText(errorMsg)) {
            return errMsgFixPattern.matcher(errorMsg).replaceFirst("");
        }
        return errorMsg;
    }

    @ExceptionHandler(ConnectorException.class)
    @ResponseBody
    public Response handleException(ConnectorException e) {
        log.error("全局拦截ConnectorException异常:", e);
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
        return ResponseI18n.buildFailure(ErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception e) {
        log.error("全局拦截Exception异常:", e);
        return ResponseI18n.buildFailure(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 未登陆
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public Response handleException(UnauthenticatedException e) {
        LogUtil.info(log, e, "全局拦截UnauthenticatedException异常:{}", e.getMessage());
        return ResponseI18n.buildFailure(ErrorCode.NO_LOGIN);
    }

    /**
     * 未授权
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Response handleException(UnauthorizedException e) {
        LogUtil.info(log, e, "全局拦截UnauthorizedException异常:{}", e.getMessage());
        return ResponseI18n.buildFailure(ErrorCode.USER_NOT_AUTH);
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
