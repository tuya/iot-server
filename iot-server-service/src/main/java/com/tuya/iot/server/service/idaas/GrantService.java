package com.tuya.iot.server.service.idaas;

import com.tuya.iot.server.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.server.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.server.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.server.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.server.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.server.ability.idaas.model.UserRevokeRolesReq;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface GrantService {
    Boolean grantPermissionToRole(String operatorUid, RoleGrantPermissionReq request);

    Boolean grantPermissionsToRole(String operatorUid, RoleGrantPermissionsReq request);

    Boolean setPermissionsToRole(String operatorUid, RoleGrantPermissionsReq request);

    Boolean revokePermissionFromRole(String spaceId, String operatorUid, String roleCode,String permissionCode);

    Boolean revokePermissionsFromRole(String operatorUid, RoleRevokePermissionsReq request);

    Boolean grantRoleToUser(String operatorUid, UserGrantRoleReq req);

    Boolean setRolesToUser(String operatorUid, UserGrantRolesReq req);

    Boolean revokeRoleFromUser(String spaceId,String operatorUid, String roleCode,String uid);

    Boolean revokeRolesFromUser(String operatorUid, UserRevokeRolesReq req);

    Boolean setRoleToUsers(String spaceId,String operatorUid, String roleCode, List<String> uidList);
}
