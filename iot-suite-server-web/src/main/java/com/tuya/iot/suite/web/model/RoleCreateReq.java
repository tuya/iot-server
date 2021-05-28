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
public class RoleCreateReq {

    @ApiModelProperty(value = "角色名称", required = true)
    String roleName;

    @ApiModelProperty(value = "角色类型", required = true)
    String roleType;

}
