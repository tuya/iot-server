package com.tuya.iot.suite.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionNodeDTO {
    @ApiModelProperty("权限名称")
    String permissionName;
    @ApiModelProperty("权限编码")
    String permissionCode;
    @ApiModelProperty("权限说明")
    String remark;
    @ApiModelProperty("权限类型，菜单/api/按钮/数据")
    String permissionType;
    @ApiModelProperty("顺序")
    Integer order;
    @ApiModelProperty("权限的依赖，权限编码列表")
    List<String> dependencies;
    @ApiModelProperty("上级权限编码")
    String parentCode;
    @ApiModelProperty("下级权限")
    List<PermissionNodeDTO> children;
}
