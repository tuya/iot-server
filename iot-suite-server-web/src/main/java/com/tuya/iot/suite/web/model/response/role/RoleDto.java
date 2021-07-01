package com.tuya.iot.suite.web.model.response.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月01日 14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
