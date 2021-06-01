package com.tuya.iot.suite.web.model.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Getter
@Setter
@ToString
public class UserAddReq implements Serializable {

    @ApiModelProperty(value="用户名",required = true)
    String userName;

    @ApiModelProperty(value="密码",required = true)
    String password;

    @ApiModelProperty(value="昵称",required = false)
    String nickName;

    @ApiModelProperty(value="城市编码",required = false)
    String countryCode;

    @ApiModelProperty(value="关联的角色列表",required = true)
    List<String> roleCodes;
}
