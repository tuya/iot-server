package com.tuya.iot.suite.ability.idaas.ability;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckAbility {

    Boolean checkPermissionForRole(Long spaceId,String permissionCode, String roleCode);

    Boolean checkRoleForUser(Long spaceId,String roleCode,String uid);

    Boolean checkPermissionForUser(Long spaceId,String permissionCode,String uid);

}
