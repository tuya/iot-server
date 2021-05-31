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
public class IdaasUser {

    /**
     * 外部uid
     */
    String uid;

    /**
     * 外部用户名
     */
    String username;

    /**
     * 备注
     */
    String remark;

    Long spaceId;

}
