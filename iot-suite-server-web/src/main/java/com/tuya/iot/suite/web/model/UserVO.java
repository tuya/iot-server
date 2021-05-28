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
public class UserVO {
    @ApiModelProperty("昵称")
    String nickName;
    @ApiModelProperty("用户名")
    String userName;
    @ApiModelProperty("创建时间")
    String createTime;
    @ApiModelProperty("创建者用户名")
    String creatorUserName;
    @ApiModelProperty("角色名称列表")
    List<String> roleNames;
}
