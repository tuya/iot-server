package com.tuya.iot.suite.web.model.response.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月01日 14:57
 */
@Getter
@Setter
@ToString
@Builder
public class RoleDto implements Serializable {
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
    private String remark;

}
