package com.tuya.iot.server.core.constant;

import lombok.Getter;

/**
 * @author benguan.zhou
 */
public enum SpaceAuthenticationType {

    IOT_UID(1,"iot开发者"),
    PROJECT(2,"项目"),
    CLIENT_ID(3,"app"),
    ;

    SpaceAuthenticationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Getter
    private Integer code;

    @Getter
    private String desc;

}
