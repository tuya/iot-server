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
public class IdaasRole {

    /**
     * 角色code
     */
    String roleCode;

    /**
     * 角色名字
     */
    String roleName;

    /**
     * 隔离空间id
     */
    Long spaceId;

    /**
     * 备注
     */
    String remark;

}
