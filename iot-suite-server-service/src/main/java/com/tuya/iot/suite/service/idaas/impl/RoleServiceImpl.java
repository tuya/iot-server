package com.tuya.iot.suite.service.idaas.impl;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility;
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.*;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.model.PageVO;
import com.tuya.iot.suite.core.util.Assertion;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;
import com.tuya.iot.suite.service.idaas.PermissionTemplateService;
import com.tuya.iot.suite.service.idaas.RoleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
@Setter
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleAbility roleAbility;
    @Autowired
    private GrantAbility grantAbility;
    @Autowired
    private IdaasUserAbility idaasUserAbility;
    @Autowired
    private PermissionAbility permissionAbility;
    @Autowired
    private PermissionTemplateService permissionTemplateService;

    @Override
    public Boolean createRole(String spaceId, RoleCreateReqDTO req) {
        // 0. check permission
        checkRoleWritePermission(spaceId, req.getUid(), req.getRoleCode());
        String roleType = RoleTypeEnum.fromRoleCode(req.getRoleCode()).name();
        List<PermissionNodeDTO> perms = permissionTemplateService.getTemplatePermissionFlattenList(roleType, Locale.CHINESE.getLanguage());
        // 1. create role
        boolean createRoleRes = roleAbility.createRole(spaceId, IdaasRoleCreateReq.builder()
                .roleCode(req.getRoleCode())
                .roleName(req.getRoleName())
                .remark(req.getRemark()).build());
        if (!createRoleRes) {
            return false;
        }
        // 2. grant permissions from template
        // if grant failure, need not rollback
        return grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId.toString())
                .roleCode(req.getRoleCode())
                .permissionCodes(perms.stream().map(it ->
                        it.getPermissionCode()).collect(Collectors.toList())
                ).build());
    }

    private void checkRoleWritePermission(String spaceId, String operatorUid, String targetRoleCode) {
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(targetRoleCode).isAdmin(), "can not write a 'admin' role!");
        //数据权限校验，校验操作者自己是否为更高的角色。
        //比如有从高到低低角色 a->b->c->d->e。当前用户有角色 a、e，修改角色c，由于当前用户存在比c高级低角色a，所以该操作是允许的。
        roleAbility.queryRolesByUser(spaceId, operatorUid).stream().filter(
                it -> RoleTypeEnum.fromRoleCode(targetRoleCode).ltEq(RoleTypeEnum.fromRoleCode(it.getRoleCode()))
        ).findAny().orElseThrow(() -> new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION));
    }

    private void checkRoleWritePermission(String spaceId, String operatorUid, Collection<String> targetRoleCodes) {
        targetRoleCodes.forEach(targetRoleCode ->
                Assert.isTrue(!RoleTypeEnum.fromRoleCode(targetRoleCode).isAdmin(), "can not write a 'admin' role!"));
        //数据权限校验，校验操作者自己是否为更高的角色。
        List<IdaasRole> roles = roleAbility.queryRolesByUser(spaceId, operatorUid);
        for (String targetRoleCode : targetRoleCodes) {
            for (IdaasRole myRole : roles) {
                boolean enabled =
                        RoleTypeEnum.fromRoleCode(targetRoleCode).ltEq(RoleTypeEnum.fromRoleCode(myRole.getRoleCode()));
                if (!enabled) {
                    throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
                }
            }
        }
    }

    private void checkRoleReadPermission(String spaceId, String operatorUid, String targetRoleCode) {
        //数据权限校验，校验操作者自己是否为更高的角色。
        roleAbility.queryRolesByUser(spaceId, operatorUid).stream().filter(
                it -> RoleTypeEnum.fromRoleCode(targetRoleCode).ltEq(RoleTypeEnum.fromRoleCode(it.getRoleCode()))
        ).findAny().orElseThrow(() -> new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION));
    }

    @Override
    public Boolean updateRole(String spaceId, String operatorUid, String roleCode, RoleUpdateReq req) {
        checkRoleWritePermission(spaceId, operatorUid, roleCode);
        return roleAbility.updateRole(spaceId, roleCode, RoleUpdateReq.builder()
                .roleName(req.getRoleName()).build());
    }

    @Override
    public Boolean deleteRole(String spaceId, String operatorUid, String roleCode) {
        checkRoleWritePermission(spaceId, operatorUid, roleCode);
        //底层api没有判断删除角色时是否存在关联的用户，那么我们就需要实现这个逻辑
        IdaasPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder()
                .roleCode(roleCode).build());
        if (pageResult.getTotalCount() > 0) {
            throw new ServiceLogicException(ErrorCode.ROLE_DEL_FAIL_FOR_RELATED_USERS);
        }
        return roleAbility.deleteRole(spaceId, roleCode);
    }


    @Override
    public IdaasRole getRole(String spaceId, String operatorUid, String roleCode) {
        //checkRoleReadPermission(spaceId,operatorUid,roleCode);
        return roleAbility.getRole(spaceId, roleCode);
    }


    @Override
    public List<IdaasRole> queryRolesByUser(String spaceId, String uid) {
        //need check read permission?
        return roleAbility.queryRolesByUser(spaceId, uid);
    }

    @Override
    public PageVO<IdaasRole> queryRolesPagination(String spaceId, RolesPaginationQueryReq req) {
        IdaasPageResult<IdaasRole> pageResult = roleAbility.queryRolesPagination(spaceId, req);
        List<IdaasRole> list = pageResult.getResults();
        return PageVO.builder().pageNo(pageResult.getPageNumber())
                .pageSize(pageResult.getPageSize())
                .total(pageResult.getTotalCount())
                .data((List) list).build();
    }

    @Override
    public boolean deleteRoles(String permissionSpaceId, String uid, Collection<String> roleCodes) {
        checkRoleWritePermission(permissionSpaceId, uid, roleCodes);
        long count = roleCodes.stream().map(roleCode -> roleAbility.deleteRole(permissionSpaceId, roleCode)).count();
        return count == roleCodes.size();
    }

    @Override
    public Boolean resetRolePermissionsFromTemplate(String spaceId, String operatorUid, String roleCode) {
        // 0. check permission
        checkRoleWritePermission(spaceId, operatorUid, roleCode);

        List<String> existPerms = permissionAbility.queryPermissionsByRoleCodes(spaceId, PermissionQueryByRolesReq.builder()
                .roleCodeList(Lists.newArrayList(roleCode))
                .build()).stream().flatMap(it -> it.getPermissionList().stream()).map(it -> it.getPermissionCode())
                .collect(Collectors.toList());
        RoleTypeEnum roleType = RoleTypeEnum.fromRoleCode(roleCode);

        // 1. get permissions from template
        List<String> templatePerms = permissionTemplateService.getTemplatePermissionFlattenList(roleType.name(), Locale.CHINESE.getLanguage())
                .stream().map(it -> it.getPermissionCode())
                .collect(Collectors.toList());

        List<String> permsToAdd = new ArrayList<>(templatePerms);
        permsToAdd.removeAll(existPerms);

        List<String> permsToDel = new ArrayList<>(existPerms);
        permsToDel.removeAll(templatePerms);
        // 2. add permissions if need
        if (!permsToAdd.isEmpty()) {
            boolean addRes = grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                    .spaceId(spaceId)
                    .permissionCodes(permsToAdd)
                    .roleCode(roleCode)
                    .build());
            if (!addRes) {
                log.info("grant permissions to role failed");
                return false;
            }
        }
        // 3. delete permissions if need
        if (!permsToDel.isEmpty()) {
            boolean delRes = grantAbility.revokePermissionsFromRole(RoleRevokePermissionsReq.builder()
                    .spaceId(spaceId)
                    .permissionCodes(permsToDel)
                    .roleCode(roleCode)
                    .build());
            if (!delRes) {
                log.info("revoke permissions from role failed");
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> checkAndRemoveOldRole(String spaceId, String uid, List<String> roleCodes, boolean removeOld) {
        List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId, uid);
        Map<String, String> roleMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (IdaasRole userRole : userRoles) {
                if(RoleTypeEnum.isAdminRoleCode(userRole.getRoleCode())){
                    throw new ServiceLogicException(ErrorCode.ADMIN_CANT_NOT_UPDATE);
                }
                roleMap.put(userRole.getRoleCode(), userRole.getRoleName());
            }
        }
        List<String> newRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleCodes)) {
            for (String roleCode : roleCodes) {
                if(RoleTypeEnum.isAdminRoleCode(roleCode)){
                    throw new ServiceLogicException(ErrorCode.ADMIN_CANT_NOT_UPDATE);
                }
                if (!roleMap.containsKey(roleCode)) {
                    newRoles.add(roleCode);
                } else {
                    roleMap.remove(roleCode);
                }
            }
        }
        if (removeOld) {
            //去除原来的角色
            roleMap.values().stream().forEach(e -> {
                Boolean removeRole = grantAbility.revokeRoleFromUser(spaceId, e, uid);
                if (removeRole) {
                    log.info("移除了uid={} 的角色roleCode={}", uid, e);
                }
            });
            return newRoles;
        }
        return newRoles;
    }

    @Override
    public RoleTypeEnum userOperateRole(String spaceId, String operatUserId) {
        List<IdaasRole> operatorRoles = roleAbility.queryRolesByUser(spaceId, operatUserId);
        Assertion.isTrue(operatorRoles.size() >= 1, "a user can at most have one role!");
        RoleTypeEnum roleType = RoleTypeEnum.normal;
        for (IdaasRole operatorRole : operatorRoles) {
            RoleTypeEnum roleTypeEnum = RoleTypeEnum.fromRoleCode(operatorRole.getRoleCode());
            if (roleType.lt(roleTypeEnum)) {
                roleType = roleTypeEnum;
            }
        }
        return roleType;
    }

}
