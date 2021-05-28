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
public class PermissionVO {
    @ApiModelProperty("权限id")
    String id;
    @ApiModelProperty("权限名称")
    String name;
    @ApiModelProperty("权限编码")
    String code;
    @ApiModelProperty("权限类型，菜单/api/按钮/数据")
    String type;
    @ApiModelProperty("权限的依赖，权限编码列表")
    List<String> dependencies;
}