package com.tuya.iot.suite.web.model.request.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月01日 15:01
 */
@Getter
@Setter
@ToString
public class RoleEditReq implements Serializable {

    /**
     *
     */
    @ApiModelProperty("角色code")
    private String roleCode;
    /**
     *
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     *
     */
    @ApiModelProperty("角色备注")
    private String roleRemark;
}
