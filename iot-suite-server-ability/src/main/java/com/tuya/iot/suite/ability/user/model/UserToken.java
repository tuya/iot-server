package com.tuya.iot.suite.ability.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/29
 */
@NoArgsConstructor
@Data
public class UserToken implements Serializable {
    private static final long serialVersionUID = 8612822646000971455L;
    private String access_token;
    private int expire;
    private String refresh_token;
    private String uid;
}
