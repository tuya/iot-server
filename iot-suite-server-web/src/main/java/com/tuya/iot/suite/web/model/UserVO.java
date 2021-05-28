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
    String nick_name;
    @ApiModelProperty("用户名")
    String user_name;
    @ApiModelProperty("创建时间")
    String create_time;
    @ApiModelProperty("创建者用户名")
    String creator_user_name;
    @ApiModelProperty("角色名称列表")
    List<String> role_names;
}
