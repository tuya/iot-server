package com.tuya.iot.suite.ability.idaas.ability;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface PermissionCheckAbility {

    Boolean checkPermissionForRole(Long spaceId,String roleCode,String permissionCode);

    Boolean checkRoleForUser(Long spaceId,String uid,String roleCode);

    Boolean checkPermissionForUser(Long spaceId,String uid,String permissionCode);

}
