package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateReq {
    @ApiModelProperty("用户名")
    String user_name;
    @ApiModelProperty("密码")
    String password;
    @ApiModelProperty("昵称")
    String nick_name;
    @ApiModelProperty("关联的角色列表")
    List<String> role_code_list;
}
