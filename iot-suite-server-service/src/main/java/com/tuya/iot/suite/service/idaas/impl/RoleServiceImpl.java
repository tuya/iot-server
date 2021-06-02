package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.IdaasPageResult;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleQueryReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.model.PageVO;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    @Override
    public Boolean createRole(Long spaceId, RoleCreateReqDTO req) {
        //TODO 用一个自定义的错误码
        //数据权限校验，校验操作者自己是否为更高的角色。
        roleAbility.queryRolesByUser(spaceId,req.getUid()).stream().filter(
                it-> RoleTypeEnum.fromRoleCode(req.getRoleCode()).isOffspringOrSelf(RoleTypeEnum.fromRoleCode(it.getRoleCode()))
        ).findAny().orElseThrow(()->new ServiceLogicException(ErrorCode.USER_NOT_AUTH));
        return roleAbility.createRole(spaceId, IdaasRoleCreateReq.builder()
                .roleCode(req.getRoleCode())
                .roleName(req.getRoleName())
                .remark(req.getRemark()).build());
    }

    @Override
    public Boolean updateRole(Long spaceId, String roleCode, RoleUpdateReq req) {
        return roleAbility.updateRole(spaceId,roleCode,RoleUpdateReq.builder()
                .roleName(req.getRoleName()).build());
    }

    @Override
    public Boolean deleteRole(Long spaceId, String roleCode) {
        return roleAbility.deleteRole(spaceId,roleCode);
    }

    @Override
    public IdaasRole getRole(Long spaceId, String roleCode) {
        return null;
    }

    @Override
    public List<IdaasRole> queryRolesByCodes(RoleQueryReq request) {
        return null;
    }

    @Override
    public List<IdaasRole> queryRolesByUser(Long spaceId, String userId) {
        //return Lists.newArrayList(IdaasRole.builder().roleCode("sysadmin").build());
        return roleAbility.queryRolesByUser(spaceId,userId);
    }

    @Override
    public PageVO<IdaasRole> queryRolesPagination(Long spaceId, RolesPaginationQueryReq req) {
        IdaasPageResult<IdaasRole> pageResult = roleAbility.queryRolesPagination(spaceId,req);
        List<IdaasRole> list = pageResult.getResults();
        return PageVO.builder().pageNo(pageResult.getPageNumber())
                .pageSize(pageResult.getPageSize())
                .total(pageResult.getTotalCount())
                .data((List)list).build();
    }

    @Override
    public boolean deleteRoles(Long permissionSpaceId, Collection<String> roleCodes) {
        long count = roleCodes.stream().map(roleCode->roleAbility.deleteRole(permissionSpaceId,roleCode)).count();
        return count == roleCodes.size();
    }
}
