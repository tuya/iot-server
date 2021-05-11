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
public class User implements Serializable {
    private static final long serialVersionUID = 2733929104492848165L;
    private String country_code;
    private String user_id;
    private String user_name;
}
