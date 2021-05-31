package com.tuya.iot.suite.service.idaas;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckService {
    boolean checkPermissionForUser(Long spaceId, String userId, String permissionCode);
}
