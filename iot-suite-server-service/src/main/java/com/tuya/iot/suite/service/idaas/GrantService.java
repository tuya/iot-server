package com.tuya.iot.suite.service.idaas;

import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.suite.ability.idaas.model.UserRevokeRolesReq;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface GrantService {
    Boolean grantPermissionToRole(String operatorUid,RoleGrantPermissionReq request);

    Boolean grantPermissionsToRole(RoleGrantPermissionsReq request);

    Boolean setPermissionsToRole(RoleGrantPermissionsReq request);

    Boolean revokePermissionFromRole(Long spaceId,String roleCode,String permissionCode);

    Boolean revokePermissionsFromRole(RoleRevokePermissionsReq request);

    Boolean grantRoleToUser(UserGrantRoleReq req);

    Boolean setRolesToUser(UserGrantRolesReq req);

    Boolean revokeRoleFromUser(Long spaceId,String uid,String roleCode);

    Boolean revokeRolesFromUser(UserRevokeRolesReq req);

    Boolean setRoleToUsers(Long spaceId, String uid, String roleCode, List<String> uidList);
}
