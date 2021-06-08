package com.tuya.iot.suite.ability.idaas.model;

import lombok.Getter;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Getter
public enum PermissionTypeEnum {
    api(1),menu(2),button(3),data(4);
    private int code;
    PermissionTypeEnum(int c){
        code = c;
    }
}
