package com.tuya.iot.suite.ability.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/26
 */
@Data
public class UserModifyRequest implements Serializable {

    private static final long serialVersionUID = -2553150282604473969L;
    private String user_id;
    private String new_password;
    private String old_password;
}
