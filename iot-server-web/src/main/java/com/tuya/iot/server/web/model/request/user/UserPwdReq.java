package com.tuya.iot.server.web.model.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class UserPwdReq implements Serializable {

    @ApiModelProperty(value="用户名",required = true)
    String userName;

    @ApiModelProperty(value="新密码",required = true)
    String newPwd;


}
