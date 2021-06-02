package com.tuya.iot.suite.web.model.request.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月01日 15:00
 */
@Getter
@Setter
@ToString
@Builder
public class RoleAddReq implements Serializable {

    /**
     *
     */
    @ApiModelProperty("角色名称")
    private String roleName;
    /**
     *
     */
    @ApiModelProperty("角色类型 /admin /manager /normal")
    private String roleType;

    /**
     *
     */
    @ApiModelProperty("角色备注")
    private String remark;
}
