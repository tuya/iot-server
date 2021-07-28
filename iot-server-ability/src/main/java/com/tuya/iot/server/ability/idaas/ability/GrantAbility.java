package com.tuya.iot.server.ability.idaas.ability;

import com.tuya.iot.server.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.server.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.server.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.server.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.server.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.server.ability.idaas.model.UserRevokeRolesReq;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface GrantAbility {

    Boolean grantPermissionToRole(RoleGrantPermissionReq request);

    Boolean grantPermissionsToRole(RoleGrantPermissionsReq request);

    Boolean setPermissionsToRole(RoleGrantPermissionsReq request);

    Boolean revokePermissionFromRole(String spaceId,String permissionCode,String roleCode);

    Boolean revokePermissionsFromRole(RoleRevokePermissionsReq request);

    Boolean grantRoleToUser(UserGrantRoleReq req);

    Boolean setRolesToUser(UserGrantRolesReq req);

    Boolean revokeRoleFromUser(String spaceId,String roleCode,String uid);

    Boolean revokeRolesFromUser(UserRevokeRolesReq req);
}
