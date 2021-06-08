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
public class PermissionQueryReq {

    /**
     * 权限标识
     */
    List<String> permissionCodeList;

    /**
     * 权限空间id
     * 没用上
     * */
    Long spaceId;
}
