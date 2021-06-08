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
public class SpaceApplyResp {

    Long gmt_create;
    Long gmt_modified;
    String space_id;
    String owner;
    String space_code;
    String space_group;
    String msg;
}
