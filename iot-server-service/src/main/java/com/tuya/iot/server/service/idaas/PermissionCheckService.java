package com.tuya.iot.server.service.idaas;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckService {
    Boolean checkPermissionForRole(String spaceIde,String permissionCode,String roleCod);

    Boolean checkRoleForUser(String spaceId,String roleCode,String uid);

    Boolean checkPermissionForUser(String spaceId,String permissionCode,String uid);}
