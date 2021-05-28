package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPermissionVO {
    @ApiModelProperty("权限id")
    String id;
    @ApiModelProperty("权限名称")
    String name;
    @ApiModelProperty("权限编码")
    String code;
    @ApiModelProperty("权限类型，页面权限/菜单权限/api权限/按钮权限")
    String type;
}
