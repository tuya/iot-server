package com.tuya.iot.suite.ability.idaas.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class RoleGrantPermissionsReq {

    /**
     * 角色code
     */
    @SerializedName("roleCode")
    String roleCode;

    /**
     * 权限标识
     */
    @SerializedName("permissionCodes")
    List<String> permissionCodes;

    /**
     * 隔离空间id
     */
    @SerializedName("spaceId")
    String spaceId;

}
