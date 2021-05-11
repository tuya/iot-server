package com.tuya.iot.suite.web.model.criteria;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/15
 */
@Data
public class UserCriteria implements Serializable {

    private static final long serialVersionUID = -7755169567156198240L;

    @ApiModelProperty(value = "用户名")
    private String user_name;

    @ApiModelProperty(value = "用户密码")
    private String login_password;

    @ApiModelProperty(value = "旧密码")
    private String current_password;

    @ApiModelProperty(value = "新密码")
    private String new_password;

    @ApiModelProperty(value = "国家")
    private String country_code;

    @ApiModelProperty(value = "电话号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "验证码")
    private String verification_code;
}
