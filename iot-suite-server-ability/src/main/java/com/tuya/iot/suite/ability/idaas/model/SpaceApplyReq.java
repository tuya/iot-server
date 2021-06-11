package com.tuya.iot.suite.ability.idaas.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceApplyReq {

    /**
     * 空间分组
     */
    @SerializedName("spaceGroup")
    String spaceGroup;

    /**
     * 空间隔离标识
     */
    @SerializedName("spaceCode")
    String spaceCode;

    /**
     * 备注
     */
    String remark;

    /**
     * 鉴权扩展字段
     */
    Integer authentication;

    /**
     * 拥有者
     */
    String owner;

}
