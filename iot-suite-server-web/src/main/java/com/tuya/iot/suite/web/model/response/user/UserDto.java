package com.tuya.iot.suite.web.model.response.user;

import com.tuya.iot.suite.web.model.response.role.RoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @ApiModelProperty("昵称")
    String nickName;
     @ApiModelProperty("用户Id")
    String userId;
    @ApiModelProperty("用户名")
    String userName;
    @ApiModelProperty("创建时间")
    String createTime;
    @ApiModelProperty("角色名称列表")
    List<RoleDto> roles ;
}
