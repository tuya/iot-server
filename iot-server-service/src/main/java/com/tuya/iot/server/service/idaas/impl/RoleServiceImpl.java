package com.tuya.iot.server.service.idaas.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tuya.iot.server.ability.idaas.ability.GrantAbility;
import com.tuya.iot.server.ability.idaas.ability.IdaasUserAbility;
import com.tuya.iot.server.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.server.ability.idaas.ability.RoleAbility;
import com.tuya.iot.server.ability.idaas.model.*;
import com.tuya.iot.server.core.constant.ErrorCode;
import com.tuya.iot.server.core.exception.ServiceLogicException;
import com.tuya.iot.server.core.model.PageVO;
import com.tuya.iot.server.service.dto.PermissionNodeDTO;
import com.tuya.iot.server.service.dto.RoleCreateReqDTO;
import com.tuya.iot.server.service.enums.RoleTypeEnum;
import com.tuya.iot.server.service.idaas.PermissionTemplateService;
import com.tuya.iot.server.service.idaas.RoleService;
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
                .spaceId(spaceId)
                .roleCode(req.getRoleCode())
                .permissionCodes(perms.stream().map(it ->
                        it.getPermissionCode()).collect(Collectors.toList())
                ).build());
    }

    private void checkRoleWritePermission(String spaceId, String operatorUid, String targetRoleCode) {
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(targetRoleCode).isAdmin(), "can not write a 'admin' role!");
        //?????????????????????????????????????????????????????????????????????
    }

    private void checkRoleWritePermission(String spaceId, String operatorUid, Collection<String> targetRoleCodes) {
        targetRoleCodes.forEach(targetRoleCode ->
                Assert.isTrue(!RoleTypeEnum.fromRoleCode(targetRoleCode).isAdmin(), "can not write a 'admin' role!"));
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
        //??????api????????????????????????????????????????????????????????????????????????????????????????????????
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

        Set<String> existPermSet = permissionAbility.queryPermissionsByRoleCodes(spaceId, PermissionQueryByRolesReq.builder()
                .roleCodeList(Lists.newArrayList(roleCode))
                .build()).stream().flatMap(it -> it.getPermissionList().stream()).map(it -> it.getPermissionCode())
                .collect(Collectors.toSet());
        RoleTypeEnum roleType = RoleTypeEnum.fromRoleCode(roleCode);

        // 1. get permissions from template
        List<String> templatePerms = permissionTemplateService.getTemplatePermissionFlattenList(roleType.name(), Locale.CHINESE.getLanguage())
                .stream().map(it -> it.getPermissionCode())
                .collect(Collectors.toList());

        Set<String> templatePermSet = Sets.newHashSet(templatePerms);

        List<String> permsToRevoke = Lists.newArrayList(Sets.difference(existPermSet,templatePermSet));
        List<String> permsToGrant = Lists.newArrayList(Sets.difference(templatePermSet,existPermSet));
        // 2. delete permissions if need
        if (!permsToRevoke.isEmpty()) {
            boolean delRes = grantAbility.revokePermissionsFromRole(RoleRevokePermissionsReq.builder()
                    .spaceId(spaceId)
                    .permissionCodes(permsToRevoke)
                    .roleCode(roleCode)
                    .build());
            if (!delRes) {
                log.info("revoke permissions from role failed");
                return false;
            }
        }
        // 3. add permissions if need
        if (!permsToGrant.isEmpty()) {
            boolean addRes = grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                    .spaceId(spaceId)
                    .permissionCodes(permsToGrant)
                    .roleCode(roleCode)
                    .build());
            if (!addRes) {
                log.info("grant permissions to role failed");
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> checkAndRemoveOldRole(String spaceId, String uid, List<String> roleCodes, boolean removeOld) {
        List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId, uid);
        //code->name
        Map<String, String> roleMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (IdaasRole userRole : userRoles) {
                if (RoleTypeEnum.isAdminRoleCode(userRole.getRoleCode())) {
                    throw new ServiceLogicException(ErrorCode.ADMIN_CANT_NOT_UPDATE);
                }
                roleMap.put(userRole.getRoleCode(), userRole.getRoleName());
            }
        }
        List<String> newRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleCodes)) {
            for (String roleCode : roleCodes) {
                if (RoleTypeEnum.isAdminRoleCode(roleCode)) {
                    throw new ServiceLogicException(ErrorCode.ADMIN_CANT_NOT_GRANT);
                }
                if (!roleMap.containsKey(roleCode)) {
                    newRoles.add(roleCode);
                } else {
                    roleMap.remove(roleCode);
                }
            }
        }
        if (removeOld) {
            //?????????????????????
            roleMap.keySet().stream().forEach(e -> {
                Boolean removeRole = grantAbility.revokeRoleFromUser(spaceId, e, uid);
                if (removeRole) {
                    log.info("?????????uid={} ?????????roleCode={}", uid, e);
                }
            });
            return newRoles;
        }
        return newRoles;
    }

    @Override
    public RoleTypeEnum userOperateRole(String spaceId, String operatUserId) {
        List<IdaasRole> operatorRoles = roleAbility.queryRolesByUser(spaceId, operatUserId);
        return userOperateRole(spaceId, operatUserId, operatorRoles.stream().map(e -> e.getRoleCode()).collect(Collectors.toList()));
    }

    @Override
    public RoleTypeEnum userOperateRole(String spaceId, String operatUserId, List<String> roleCodes) {
        if(roleCodes!=null){
            Set<RoleTypeEnum> types = roleCodes.stream().map(it->
                    RoleTypeEnum.fromRoleCode(it)
            ).collect(Collectors.toSet());
            RoleTypeEnum[] orderedTypes = new RoleTypeEnum[]{RoleTypeEnum.admin,RoleTypeEnum.manager,RoleTypeEnum.normal};
            for(RoleTypeEnum type: orderedTypes){
                if(types.contains(type)){
                    return type;
                }
            }
        }
        return RoleTypeEnum.normal;
    }

}
