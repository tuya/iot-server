package com.tuya.iot.suite.web.model.request.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author mickey
 * @date 2021年06月01日 15:02
 */
@Getter
@Setter
@ToString
public class RolePermissionReq implements Serializable {

    @ApiModelProperty("角色code")
    private String roleCode;

    @ApiModelProperty("需要授权的权限code集合")
    private List<String> permissionCodes;
}
