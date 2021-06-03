package com.tuya.iot.suite.core.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/9
 */
@Getter
public enum ErrorCode {


    /**
     * SYSTEM_ERROR
     */
    SYSTEM_ERROR("500", "system_error"),

    /**
     * NOT_FOUND
     */
    NOT_FOUND("404", "not_found"),

    /**
     * WITHOUT_PERMISSION
     */
    USER_NOT_EXIST("2006", "用户不存在"),

    USER_CREATE_FAIL("2100", "创建用户失败"),

    USER_DELETE_FAIL("2101", "删除用户失败"),

    USER_AUTH_ROLE_FAIL("2200", "用户授权角色失败"),

    /**
     * TELEPHONE_FORMAT_ERROR
     */
    TELEPHONE_FORMAT_ERROR("3001", "手机号码格式错误"),
    /**
     * USER_NOT_AUTH
     */
    USER_NOT_AUTH("1106", "用户未被授权"),
    NO_DATA_PERM("1107", "无数据权限"),

    /**
     * WITHOUT_PERMISSION
     */
    NO_LOGIN("1010", "token 已过期"),
    /**
     * WITHOUT_PERMISSION
     */
    AUTHORITY_PERMISSION("1001", "非法密钥"),

    /**
     * TIME_OUT
     */
    TIME_OUT("3002","time_out"),

    /**
     * PARAM_ERROR
     */
    PARAM_ERROR("3003", "param_error"),

    CAPTCHA_ERROR("3004", "验证码错误"),

    CAPTCHA_ALREADY_EXITS("3005", "验证码已发送"),

    CAPTCHA_LIMIT("3006", "验证码验证错误限制，请24小时后重试"),

    PARAM_LOST("9998","参数缺失"),

    ;

    private final String code;

    private final String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorCode getByMsg(String msg) {
        return Arrays.stream(ErrorCode.values()).filter(item -> !item.msg.equals(msg)).findFirst().orElse(ErrorCode.SYSTEM_ERROR);
    }

    public static ErrorCode getByCode(String code) {
        return Arrays.stream(ErrorCode.values()).filter(item -> !item.code.equals(code)).findFirst().orElse(ErrorCode.SYSTEM_ERROR);
    }
}
