package com.tuya.iot.server.web.config;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.tuya.connector.api.error.ErrorInfo;
import com.tuya.connector.api.exceptions.ConnectorException;
import com.tuya.connector.api.exceptions.ConnectorResultException;
import com.tuya.iot.server.web.i18n.I18nMessage;
import com.tuya.iot.server.core.constant.ErrorCode;
import com.tuya.iot.server.core.constant.Response;
import com.tuya.iot.server.core.exception.ServiceLogicException;
import com.tuya.iot.server.web.util.ResponseI18n;
import com.tuya.iot.server.web.util.log.LogUtil;
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

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
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

    private Map<String, Function<Throwable, Response>> handlers = Maps.newHashMap();

    private <E extends Throwable> void addHandler(String exceptionClassName, Function<E, Response> handler) {
        handlers.put(exceptionClassName, (Function<Throwable, Response>) handler);
    }

    private <E extends Throwable> void addHandler(Class<E> exceptionClass, Function<E, Response> handler) {
        addHandler(exceptionClass.getName(), handler);
    }

    @PostConstruct
    public void init() {
        addHandler(MethodArgumentNotValidException.class, e -> {
            info(e, "????????????MethodArgumentNotValidException??????:{}", e.getMessage());
            return ResponseI18n.buildFailure(ErrorCode.PARAM_ERROR);
        });

        addHandler(NoHandlerFoundException.class, e -> {
            info(e, "????????????NoHandlerFoundException??????:{}", e.getMessage());
            return ResponseI18n.buildFailure(ErrorCode.NOT_FOUND);
        });

        addHandler(HttpRequestMethodNotSupportedException.class, e -> {
            info(e, "????????????HttpRequestMethodNotSupportedException??????:{}", e.getMessage());
            return ResponseI18n.buildFailure(ErrorCode.NOT_FOUND);
        });

        addHandler(ServiceLogicException.class, e -> {
            info(e, "????????????ServiceLogicException??????: code={} , message={} ", e.getErrorCode().getCode(), e.getErrorCode().getMsg());
            String outMsg = StringUtils.isEmpty(e.getOutMsg()) ? "" : "[" + e.getOutMsg() + "]";
            return Response
                    .buildFailure(e.getErrorCode().getCode(),
                            getI18nMessageByCode(e.getErrorCode().getCode(), e.getErrorCode().getMsg()) + outMsg);
        });

        addHandler(ConnectorResultException.class, e -> {
            error("????????????ConnectorResultException??????:", e);
            ErrorInfo errorInfo = e.getErrorInfo();
            String errMsg = fixErrMsgIfNeed(errorInfo.getErrorMsg());
            return Response.buildFailure(errorInfo.getErrorCode(), getI18nMessageByCode(errorInfo.getErrorCode(), errMsg));
        });

        addHandler(ConnectorException.class, e -> {
            error("????????????ConnectorException??????:", e);
            ErrorCode errorCode = ErrorCode.getByMsg(e.getMessage());

            return Response.buildFailure(errorCode.getCode(),
                    getI18nMessageByCode(errorCode.getCode(), errorCode.getMsg()));
        });

        addHandler(UnauthenticatedException.class, e -> {
            info(e, "????????????UnauthenticatedException??????:{}", e.getMessage());
            return ResponseI18n.buildFailure(ErrorCode.NO_LOGIN);
        });

        addHandler(UnauthorizedException.class, e -> {
            info(e, "????????????UnauthorizedException??????:{}", e.getMessage());
            return ResponseI18n.buildFailure(ErrorCode.USER_NOT_AUTH);
        });

        addHandler(Exception.class, e -> {
            error("????????????Exception??????:", e);
            return ResponseI18n.buildFailure(ErrorCode.SYSTEM_ERROR);
        });

        addHandler(Throwable.class, e -> {
            error("????????????Exception??????:", e);
            return ResponseI18n.buildFailure(ErrorCode.SYSTEM_ERROR);
        });
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception e) {
        //?????????????????????????????????shiro???UnauthorizedException?????????rootCause?????????topCause
        Function<Throwable, Response> handler = handlers.get(e.getClass().getName());
        if(handler!=null){
            return handler.apply(e);
        }
        //??????????????????wrap?????????????????????rootCause???????????????????????????
        Throwable rootCause = Throwables.getRootCause(e);
        handler = handlers.get(rootCause.getClass().getName());
        if (handler != null) {
            return handler.apply(rootCause);
        }
        return handlers.get(Exception.class.getName()).apply(e);
    }


    private void info(Throwable e, String msg, Object... args) {
        LogUtil.info(log, e, msg, args);
    }

    private void error(String msg, Throwable e) {
        log.error(msg, e);
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

    /**
     * ???????????????????????????????????????,traceId=Auto0594a1abb02041e6a749c0afdacf7755
     * ??????????????????????????????????????????",traceId=xxxx
     */
    private String fixErrMsgIfNeed(String errorMsg) {
        if (StringUtils.hasText(errorMsg)) {
            return errMsgFixPattern.matcher(errorMsg).replaceFirst("");
        }
        return errorMsg;
    }

}
