package com.tuya.iot.suite.service.idaas;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckService {
    Boolean checkPermissionForRole(Long spaceIde,String permissionCode,String roleCod);

    Boolean checkRoleForUser(Long spaceId,String roleCode,String uid);

    Boolean checkPermissionForUser(Long spaceId,String permissionCode,String uid);}
