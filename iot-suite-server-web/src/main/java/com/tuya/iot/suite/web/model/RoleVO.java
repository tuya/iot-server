package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleVO {
    @ApiModelProperty("角色编码")
    String code;
    @ApiModelProperty("角色名称")
    String name;
    @ApiModelProperty("角色类型编码")
    String type_code;
}
