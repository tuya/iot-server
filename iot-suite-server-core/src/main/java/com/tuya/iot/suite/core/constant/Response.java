package com.tuya.iot.suite.core.constant;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @param <T>
 * @author Chyern
 * @since 2021/3/9
 */
@ApiModel("返回模型")
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -6790872617631851223L;

    /**
     * request is success or failure
     */
    @ApiModelProperty(value = "请求是否成功")
    private Boolean success;

    /**
     * general code for error
     */
    @ApiModelProperty(value = "返回编码")
    private String code;

    /**
     * a message when request is failure
     */
    @ApiModelProperty(value = "返回信息")
    private String msg;

    /**
     * the result of request
     */
    @ApiModelProperty(value = "返回对象")
    private T result;

    private Response() {
    }

    /**
     * @param result 返回对象
     */
    public Response(T result) {
        this(true, null, "", result);
    }

    /**
     * @param success
     * @param code
     * @param msg
     * @param result
     */
    public Response(Boolean success, String code, String msg, T result) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static <T> Response <T> buildSuccess(T result) {
        return new Response<>(true, null, "", result);
    }

    public static Response buildFailure(ErrorCode errorCode) {
        return buildFailure(errorCode.getCode(), errorCode.getMsg());
    }

    public static Response buildFailure(String code, String msg) {
        Response resp = new Response();
        resp.success = false;
        resp.code = code;
        resp.msg = msg;
        resp.result = null;
        return resp;
    }


}
