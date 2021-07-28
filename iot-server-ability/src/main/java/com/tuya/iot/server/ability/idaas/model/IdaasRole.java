package com.tuya.iot.server.ability.idaas.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;

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
public class IdaasRole {

    /**
     * 角色code
     */
    String roleCode;

    /**
     * 角色名字
     */
    String roleName;

    /**
     * 隔离空间id
     */
    String spaceId;

    /**
     * 备注
     */
    String remark;

}
