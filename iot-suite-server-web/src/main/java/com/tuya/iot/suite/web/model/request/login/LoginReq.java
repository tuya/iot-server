package com.tuya.iot.suite.web.model.request.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReq {
    @ApiModelProperty(value = "用户名")
    private String user_name;

    @ApiModelProperty(value = "用户密码")
    private String login_password;

    @ApiModelProperty(value = "国家")
    private String country_code;

    @ApiModelProperty(value = "电话号码")
    private String telephone;

}
