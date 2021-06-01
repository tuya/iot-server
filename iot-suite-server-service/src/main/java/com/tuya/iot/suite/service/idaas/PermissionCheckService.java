package com.tuya.iot.suite.service.idaas;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckService {
    Boolean checkPermissionForRole(Long spaceId,String roleCode,String permissionCode);

    Boolean checkRoleForUser(Long spaceId,String uid,String roleCode);

    Boolean checkPermissionForUser(Long spaceId,String uid,String permissionCode);}
