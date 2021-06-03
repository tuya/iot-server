package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionReq;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleRevokePermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq;
import com.tuya.iot.suite.ability.idaas.model.UserGrantRolesReq;
import com.tuya.iot.suite.ability.idaas.model.UserRevokeRolesReq;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.RoleType;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GrantServiceImpl implements GrantService {
    @Autowired
    GrantAbility grantAbility;
    @Autowired
    RoleAbility roleAbility;

    @Autowired
    PermissionAbility permissionAbility;

    @Override
    public Boolean grantPermissionToRole(String operatorUid,RoleGrantPermissionReq req) {
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(req.getRoleCode()).isAdmin(), "can grant permission to a admin role!");
        List<IdaasPermission> perms = permissionAbility.queryPermissionsByUser(req.getSpaceId(),operatorUid);
        List<IdaasRole> roles = roleAbility.queryRolesByUser(req.getSpaceId(), operatorUid);
        Assert.isTrue(roles.size()==1, "a user can at most have one role!");
        IdaasRole role = roles.get(0);
        //操作者角色更高级
        if(!RoleTypeEnum.valueOf(req.getRoleCode()).isOffspringOrSelf(role.getRoleCode())){
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERM);
        }
        //操作者拥有该权限
        if(!perms.stream().anyMatch(it->it.getPermissionCode().equals(req.getPermissionCode()))){
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERM);
        }
        return grantAbility.grantPermissionToRole(req);
    }

    @Override
    public Boolean grantPermissionsToRole(RoleGrantPermissionsReq request) {
        return grantAbility.grantPermissionsToRole(request);
    }

    @Override
    public Boolean setPermissionsToRole(RoleGrantPermissionsReq request) {
        return null;
    }

    @Override
    public Boolean revokePermissionFromRole(Long spaceId, String roleCode, String permissionCode) {
        return null;
    }

    @Override
    public Boolean revokePermissionsFromRole(RoleRevokePermissionsReq request) {
        return null;
    }

    @Override
    public Boolean grantRoleToUser(UserGrantRoleReq req) {
        return null;
    }

    @Override
    public Boolean setRolesToUser(UserGrantRolesReq req) {
        return null;
    }

    @Override
    public Boolean revokeRoleFromUser(Long spaceId, String uid, String roleCode) {
        return null;
    }

    @Override
    public Boolean revokeRolesFromUser(UserRevokeRolesReq req) {
        return null;
    }

    @Override
    public Boolean setRoleToUsers(Long spaceId, String operatorUid, String roleCode, List<String> uidList) {
        //不能把系统管理员角色设置给用户
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(roleCode).isAdmin(), "can not set 'admin' role to any users!");
        //操作者有更高级的角色（或相同的角色），才可以把这个角色设置给用户
        List<IdaasRole> operatorRoles = roleAbility.queryRolesByUser(spaceId, operatorUid);
        Assert.isTrue(operatorRoles.size()==1, "a user can at most have one role!");
        String operateRoleCode = operatorRoles.get(0).getRoleCode();
        if(!RoleTypeEnum.fromRoleCode(roleCode).isOffspringOrSelf(operateRoleCode)){
            throw new ServiceLogicException(ErrorCode.NO_DATA_PERM);
        }
        //被设置角色的用户，如果有比操作者更高级的角色，则不允许操作。（不允许 普通员工 把 部门经理 设置为 项目经理）
        RoleTypeEnum operatorRoleType = RoleTypeEnum.fromRoleCode(operateRoleCode);

        for (String uid : uidList) {
            List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId,uid);
            if(!operatorRoleType.isAllOffspringOrSelf(userRoles.stream().map(it->it.getRoleCode()).collect(Collectors.toList()))){
                throw new ServiceLogicException(ErrorCode.NO_DATA_PERM);
            }
        }
        //逐个授权
        boolean success;
        for (String uid : uidList) {
            success = grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                    .spaceId(spaceId)
                    .uid(uid)
                    .roleCode(roleCode).build());
            if(!success){
                return false;
            }
        }
        return true;
    }
}
