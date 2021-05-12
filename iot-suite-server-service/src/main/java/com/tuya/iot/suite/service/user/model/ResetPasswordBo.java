package com.tuya.iot.suite.service.user.model;

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
public class ResetPasswordBo {

    String language;

    String mail;

    String countryCode;

    String phone;

    String code;

    String password;
}
