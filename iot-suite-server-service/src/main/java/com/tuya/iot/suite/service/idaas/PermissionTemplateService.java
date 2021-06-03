package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.service.dto.PermissionNodeDTO;

import java.util.Set;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/03
 */
public interface PermissionTemplateService {

    PermissionNodeDTO getPermissionTemplate(String roleType);

    Set<String> getAuthorizablePermissions();

}
