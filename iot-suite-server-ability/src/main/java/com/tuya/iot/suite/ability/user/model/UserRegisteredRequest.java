package com.tuya.iot.suite.ability.user.model;

import lombok.*;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/26
 */
@Getter
@Setter
@ToString
@Builder
public class UserRegisteredRequest implements Serializable {

    private static final long serialVersionUID = 7026392351794858299L;
    private String username;
    private String password;
    private String nick_name;
    private String country_code;

}
