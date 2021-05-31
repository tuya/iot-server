package com.tuya.iot.suite.ability.idaas.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleGrantPermissionsReq {

    /**
     * 角色code
     */
    String roleCode;

    /**
     * 权限标识
     */
    List<String> permissionCodes;

    /**
     * 隔离空间id
     */
    Long spaceId;

}
