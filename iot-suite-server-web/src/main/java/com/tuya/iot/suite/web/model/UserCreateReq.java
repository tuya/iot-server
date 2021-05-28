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
    @ApiModelProperty(value="用户名",required = true)
    String userName;
    @ApiModelProperty(value="密码",required = true)
    String password;
    @ApiModelProperty(value="昵称",required = true)
    String nickName;
    @ApiModelProperty(value="关联的角色列表",required = true)
    List<String> roleCodeList;
}
