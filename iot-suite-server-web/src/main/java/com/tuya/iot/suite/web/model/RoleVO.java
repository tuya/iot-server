package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleVO {
    @ApiModelProperty("角色编码")
    String roleCode;
    @ApiModelProperty("角色名称")
    String roleName;
    /*@ApiModelProperty("角色类型编码")
    String typeCode;*/
    @ApiModelProperty("说明")
    String remark;
}
