package com.tuya.iot.suite.ability.idaas.model;

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
public class IdaasRoleCreateReq {

    /**
     * 角色code
     */
    @SerializedName("roleCode")
    String roleCode;

    /**
     * 角色名字
     */
    @SerializedName("roleName")
    String roleName;

    /**
     * 备注
     */
    String remark;

}
