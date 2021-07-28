package com.tuya.iot.server.ability.notice.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/19
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordReq {

    String username;

    String new_password;
}
