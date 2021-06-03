package com.tuya.iot.suite.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: mickey.yin
 * @date: 2021/05/28
 */
@Getter
@Setter
@ToString
public class PermissionTemplate implements Serializable {

    @ApiModelProperty("权限名")
    private String permissionName;

    @ApiModelProperty("权限类型 api/menu/button/data")
    private String type;
    @ApiModelProperty("权限code")
    private String permissionCode;
    @ApiModelProperty("权限上级code")
    private String parentCode;
    @ApiModelProperty("权限排序")
    private Integer order;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("权限依赖")
    private String dependency;
    @ApiModelProperty("子权限集合")
    private List<PermissionTemplate> permissionList;
}
