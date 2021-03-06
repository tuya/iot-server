package com.tuya.iot.server.ability.idaas.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCreateReq {

    /**
     * 权限标识
     */
    @SerializedName("permissionCode")
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
    @SerializedName("parentCode")
    String parentCode;

    /**
     * 展示顺序
     */
    Integer order;

    /**
     * 备注
     */
    String remark;

    @SerializedName("spaceId")
    String spaceId;

}
