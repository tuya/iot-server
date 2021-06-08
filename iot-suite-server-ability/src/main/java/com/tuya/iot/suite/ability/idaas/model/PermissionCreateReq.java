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
public class PermissionCreateReq {

    /**
     * 权限标识
     */
    String permissionCode;

    /**
     * 显示名称
     */
    String name;

    /**
     * 权限类型(api/menu/button/data)
     */
    Integer type;

    /**
     * 父级权限code
     */
    String parentCode;

    /**
     * 展示顺序
     */
    Integer order;

    /**
     * 备注
     */
    String remark;

    String spaceId;

}
