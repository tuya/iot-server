package com.tuya.iot.server.web.model.criteria;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListCriteria {
    @ApiModelProperty("搜索关键词")
    String searchKey;
    @ApiModelProperty("角色编码")
    String roleCode;
}
