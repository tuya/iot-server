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
public class UserEditReq implements Serializable {

    @ApiModelProperty(value="用户Id",required = true)
    String userId;

    @ApiModelProperty(value="昵称",required = false)
    String nickName;

    @ApiModelProperty(value="关联的角色列表",required = true)
    List<String> roleCodes;
}
