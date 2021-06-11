package com.tuya.iot.suite.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateReqDTO {

    String uid;
    /**
     * 角色code
     */
    String roleCode;

    /**
     * 角色名字
     */
    String roleName;

    /**
     * 备注
     */
    String remark;

}
