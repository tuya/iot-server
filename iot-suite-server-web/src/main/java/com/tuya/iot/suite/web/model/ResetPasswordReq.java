package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/12
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordReq {

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "语言")
    String language;

    @ApiModelProperty(value = "邮件")
    String mail;

    @ApiModelProperty(value = "国家码")
    String countryCode;

    @ApiModelProperty(value = "手机号")
    String phone;

    @ApiModelProperty(value = "新密码")
    String newPassword;
}
