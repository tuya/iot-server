package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleTypeVO {
    @ApiModelProperty("角色类型名称，系统管理员/管理员/普通用户")
    String name;
    @ApiModelProperty("角色类型编码")
    String code;
}
