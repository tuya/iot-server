package com.tuya.iot.server.ability.idaas.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @description 用户 批量角色 授权 请求
 * @author benguan.zhou@tuya.com
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGrantRolesReq {

    String uid;

    @SerializedName("roleCodeList")
    List<String> roleCodeList;

    /**
     * 隔离空间id
     */
    @SerializedName("spaceId")
    String spaceId;

}
