package com.tuya.iot.suite.ability.idaas.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionQueryByRolesRespItem {
    String roleCode;
    List<IdaasPermission> permissionModels;
}
