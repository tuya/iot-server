package com.tuya.iot.suite.core.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * Description  错误码
 * 注意：要区分云端返回的错误码和本系统自定义的错误码。
 * 区分方式通过前缀。有前缀"e-"的是本系统定义的错误码。
 * 如果云端错误码也有"e-"前缀怎么办？不会的，他们的错误码是数字。
 * 万一有，到时候通过aop重写他们的错误码前缀。
 * 为什么要区分？
 * 不区分的话，我们定义的错误码和他们的错误码可能有冲突。
 * 之前的就不改了，从v1.1.2开始使用前缀方案。
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
    SYSTEM_TIP("e-100", "系统提示"),

    /**
     * NOT_FOUND
     */
    NOT_FOUND("404", "not_found"),

    /**
     * WITHOUT_PERMISSION
     */
    USER_NOT_EXIST("2006", "用户不存在"),

    USER_CREATE_FAIL("e-2100", "创建用户失败"),

    USER_DELETE_FAIL("e-2101", "删除用户失败"),

    USER_DELETE_COUNT_CAN_NOT_MORE_THAN_20("e-2102", "一次性删除用户不能超过20个"),

    USER_AUTH_ROLE_FAIL("e-2200", "用户授权角色失败"),

    /**
     * TELEPHONE_FORMAT_ERROR
     */
    TELEPHONE_FORMAT_ERROR("3001", "手机号码格式错误"),
    /**
     * USER_NOT_AUTH
     */
    USER_NOT_AUTH("1106", "用户未被授权"),

    /**
     * NO_DATA_PERM
     * */
    NO_DATA_PERMISSION("e-1107", "无数据权限"),

    /**
     * ROLE_DEL_FAIL_FOR_RELATED_USERS
     * */
    ROLE_DEL_FAIL_FOR_RELATED_USERS("e-1108","角色删除失败，存在关联用户"),

     ADMIN_CANT_NOT_UPDATE ("e-1109","admin不可被修改角色") ,

     ADMIN_CANT_NOT_GRANT ("e-1110","不允许修改为admin") ,

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
    PARAM_ERROR("e-3003", "param_error"),

    CAPTCHA_ERROR("3004", "验证码错误"),

    CAPTCHA_ALREADY_EXITS("3005", "验证码已发送"),

    CAPTCHA_LIMIT("3006", "验证码验证错误限制，请24小时后重试"),

    PARAM_LOST("e-9998","参数缺失"),

    ;

    private final String code;

    private final String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorCode getByMsg(String msg) {
        return Arrays.stream(ErrorCode.values()).filter(item -> item.msg.equals(msg)).findFirst().orElse(ErrorCode.SYSTEM_ERROR);
    }

    public static ErrorCode getByCode(String code) {
        return Arrays.stream(ErrorCode.values()).filter(item -> item.code.equals(code)).findFirst().orElse(ErrorCode.SYSTEM_ERROR);
    }
}
