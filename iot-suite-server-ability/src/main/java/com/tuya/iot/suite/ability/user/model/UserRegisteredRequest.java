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
public class UserRegisteredRequest implements Serializable {

    private static final long serialVersionUID = 7026392351794858299L;
    private String username;
    private String user_name;
    private String password;
    private String country_code;
}
