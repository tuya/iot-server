package com.tuya.iot.server.service.idaas;

import com.tuya.iot.server.service.dto.PermissionNodeDTO;

import java.util.List;
import java.util.Set;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/03
 */
public interface PermissionTemplateService {

    List<PermissionNodeDTO> getTemplatePermissionTrees(String roleType,String lang);

    List<PermissionNodeDTO> getTemplatePermissionFlattenList(String roleType,String lang);

    Set<String> getAuthorizablePermissions();

}
