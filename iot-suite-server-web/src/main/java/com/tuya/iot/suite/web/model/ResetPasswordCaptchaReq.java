package com.tuya.iot.suite.web.model;

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

    String language;

    String mail;

    String countryCode;

    String phone;
}
