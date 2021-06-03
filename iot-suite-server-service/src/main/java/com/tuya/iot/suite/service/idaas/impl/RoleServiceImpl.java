package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasPageResult;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.util.LazyRef;
import com.tuya.iot.suite.core.util.Tuple2;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.model.PageVO;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import com.tuya.iot.suite.service.util.PermTemplateUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
@Setter
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleAbility roleAbility;
    @Autowired
    private GrantAbility grantAbility;

    /**
     * roleType=>permissionTemplate
     *
     * load only when first call
     */
    private LazyRef<Map<String, PermissionNodeDTO>> rolePermissionTmplMapRef = LazyRef.lateInit(() ->
            Stream.of(RoleTypeEnum.values()).map(it ->
                new Tuple2<>(it.name(), PermTemplateUtil
                        .load("classpath:template/permissions-" + it.name() + ".json"))
            ).collect(Collectors.toMap(it -> it.first(), it -> it.second()))
    );

    /**
     * roleType=>permissionList
     */
    private LazyRef<Map<String, List<PermissionNodeDTO>>> rolePermissionsMapRef = LazyRef.lateInit(() ->
            Stream.of(RoleTypeEnum.values()).map(it ->
                    new Tuple2<>(it.name(), PermTemplateUtil
                            .loadAsList("classpath:template/permissions-" + it.name() + ".json"))
            ).collect(Collectors.toMap(it -> it.first(), it -> it.second()))
    );

    @Override
    public PermissionNodeDTO getPermissionTemplate(String roleType) {
        return rolePermissionTmplMapRef.get().get(roleType);
    }

    @Override
    public Boolean createRole(Long spaceId, RoleCreateReqDTO req) {
        checkRoleWritePermission(spaceId, req.getUid(), req.getRoleCode());
        String roleType = RoleTypeEnum.fromRoleCode(req.getRoleCode()).name();
        List<PermissionNodeDTO> perms = rolePermissionsMapRef.get().get(roleType);
        boolean createRoleRes = roleAbility.createRole(spaceId, IdaasRoleCreateReq.builder()
                .roleCode(req.getRoleCode())
                .roleName(req.getRoleName())
                .remark(req.getRemark()).build());
        if (!createRoleRes) {
            return false;
        }
        //if grant failure, need not rollback
        return grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode(req.getRoleCode())
                .permissionCodes(perms.stream().map(it ->
                        it.getPermissionCode()).collect(Collectors.toList())
                ).build());
    }

    private void checkRoleWritePermission(Long spaceId, String operatorUid, String targetRoleCode) {
        Assert.isTrue(!RoleTypeEnum.fromRoleCode(targetRoleCode).isAdmin(), "can not write a 'admin' role!");
        //数据权限校验，校验操作者自己是否为更高的角色。
        roleAbility.queryRolesByUser(spaceId, operatorUid).stream().filter(
                it -> RoleTypeEnum.fromRoleCode(targetRoleCode).isOffspringOrSelf(RoleTypeEnum.fromRoleCode(it.getRoleCode()))
        ).findAny().orElseThrow(() -> new ServiceLogicException(ErrorCode.NO_DATA_PERM));
    }

    private void checkRoleWritePermission(Long spaceId, String operatorUid, Collection<String> targetRoleCodes) {
        targetRoleCodes.forEach(targetRoleCode ->
                Assert.isTrue(!RoleTypeEnum.fromRoleCode(targetRoleCode).isAdmin(), "can not write a 'admin' role!"));
        //数据权限校验，校验操作者自己是否为更高的角色。
        List<IdaasRole> roles = roleAbility.queryRolesByUser(spaceId, operatorUid);
        for (String targetRoleCode : targetRoleCodes) {
            for (IdaasRole myRole : roles) {
                boolean enabled =
                        RoleTypeEnum.fromRoleCode(targetRoleCode).isOffspringOrSelf(RoleTypeEnum.fromRoleCode(myRole.getRoleCode()));
                if (!enabled) {
                    throw new ServiceLogicException(ErrorCode.NO_DATA_PERM);
                }
            }
        }
    }

    private void checkRoleReadPermission(Long spaceId, String operatorUid, String targetRoleCode) {
        //数据权限校验，校验操作者自己是否为更高的角色。
        roleAbility.queryRolesByUser(spaceId, operatorUid).stream().filter(
                it -> RoleTypeEnum.fromRoleCode(targetRoleCode).isOffspringOrSelf(RoleTypeEnum.fromRoleCode(it.getRoleCode()))
        ).findAny().orElseThrow(() -> new ServiceLogicException(ErrorCode.NO_DATA_PERM));
    }

    @Override
    public Boolean updateRole(Long spaceId, String operatorUid, String roleCode, RoleUpdateReq req) {
        checkRoleWritePermission(spaceId, operatorUid, roleCode);
        return roleAbility.updateRole(spaceId, roleCode, RoleUpdateReq.builder()
                .roleName(req.getRoleName()).build());
    }

    @Override
    public Boolean deleteRole(Long spaceId, String operatorUid, String roleCode) {
        checkRoleWritePermission(spaceId, operatorUid, roleCode);
        return roleAbility.deleteRole(spaceId, roleCode);
    }


    @Override
    public IdaasRole getRole(Long spaceId, String operatorUid, String roleCode) {
        //checkRoleReadPermission(spaceId,operatorUid,roleCode);
        return roleAbility.getRole(spaceId, roleCode);
    }


    @Override
    public List<IdaasRole> queryRolesByUser(Long spaceId, String uid) {
        //need check read permission?
        return roleAbility.queryRolesByUser(spaceId, uid);
    }

    @Override
    public PageVO<IdaasRole> queryRolesPagination(Long spaceId, RolesPaginationQueryReq req) {
        IdaasPageResult<IdaasRole> pageResult = roleAbility.queryRolesPagination(spaceId, req);
        List<IdaasRole> list = pageResult.getResults();
        return PageVO.builder().pageNo(pageResult.getPageNumber())
                .pageSize(pageResult.getPageSize())
                .total(pageResult.getTotalCount())
                .data((List) list).build();
    }

    @Override
    public boolean deleteRoles(Long permissionSpaceId, String uid, Collection<String> roleCodes) {
        checkRoleWritePermission(permissionSpaceId, uid, roleCodes);
        long count = roleCodes.stream().map(roleCode -> roleAbility.deleteRole(permissionSpaceId, roleCode)).count();
        return count == roleCodes.size();
    }
}
