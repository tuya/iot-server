package com.tuya.iot.server.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordCaptchaReq {

    @ApiModelProperty(value = "语言")
    String language;

    @ApiModelProperty(value = "邮件")
    String mail;

    @ApiModelProperty(value = "国家码")
    String countryCode;

    @ApiModelProperty(value = "手机号")
    String phone;
}
