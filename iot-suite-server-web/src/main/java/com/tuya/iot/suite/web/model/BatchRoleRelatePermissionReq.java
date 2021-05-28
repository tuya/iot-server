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
public class BatchRoleRelatePermissionReq {

    @ApiModelProperty(value = "角色code列表", required = true)
    List<String> roleCodeList;

    @ApiModelProperty(value = "权限code列表", required = true)
    List<String> permissionCodeList;

}
