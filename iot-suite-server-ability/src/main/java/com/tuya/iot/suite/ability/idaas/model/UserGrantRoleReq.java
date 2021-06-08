package com.tuya.iot.suite.ability.idaas.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserGrantRoleReq {

    /**
     * 角色code
     */
    String roleCode;


    String uid;

    /**
     * 隔离空间id
     */
    String spaceId;

}
