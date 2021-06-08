package com.tuya.iot.suite.ability.idaas.model;

import com.google.gson.annotations.SerializedName;
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
public class UserRevokeRolesReq {

    String uid;

    @SerializedName("roleCodeList")
    List<String> roleCodeList;

    /**
     * 隔离空间id
     */
    @SerializedName("spaceId")
    String spaceId;

}
