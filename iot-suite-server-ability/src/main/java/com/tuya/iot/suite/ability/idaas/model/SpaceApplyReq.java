package com.tuya.iot.suite.ability.idaas.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SpaceApplyReq {

    /**
     * 空间分组
     */
    String spaceGroup;

    /**
     * 空间隔离标识
     */
    String spaceCode;

    /**
     * 备注
     */
    String remark;

    /**
     * 鉴权扩展字段
     */
    String authentication;

    String owner;

}
