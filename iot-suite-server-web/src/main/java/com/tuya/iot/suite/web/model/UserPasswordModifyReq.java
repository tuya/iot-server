package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author benguan.zhou@tuya.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPasswordModifyReq {
    @ApiModelProperty(value = "用户id", required = true)
    String uid;
    @ApiModelProperty(value = "旧密码", required = true)
    String oldPassword;
    @ApiModelProperty(value = "新密码", required = true)
    String newPassword;
}
