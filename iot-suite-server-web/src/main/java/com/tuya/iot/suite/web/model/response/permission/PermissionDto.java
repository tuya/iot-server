package com.tuya.iot.suite.web.model.response.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月01日 15:06
 */
@Getter
@Setter
@ToString
public class PermissionDto implements Serializable {


    @ApiModelProperty("角色code,唯一标志")
    private String permissionCode;
    @ApiModelProperty("角色名")
    private String permissionName;
    @ApiModelProperty("角色类型 api menu data")
    private String permissionType;

    @ApiModelProperty("角色上级code")
    private String parentCode;

    @ApiModelProperty("角色排序")
    private Integer order;

    @ApiModelProperty("备注")
    private String remark;


}
