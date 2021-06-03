package com.tuya.iot.suite.ability.idaas.model;

import com.tuya.iot.suite.ability.model.PageRequestInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class IdaasUserPageReq extends PageRequestInfo {

    /**
     * 角色名
     */
    String roleName;

    /**
     * 外部用户名
     */
    String username;

    /**
     * 角色code
     */
    String roleCode;

}
