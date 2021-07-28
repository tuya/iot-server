package com.tuya.iot.server.service.idaas.impl;

import com.tuya.iot.server.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.server.ability.idaas.model.*;
import com.tuya.iot.server.service.idaas.PermissionService;
import com.tuya.iot.server.service.dto.PermissionNodeDTO;
import com.tuya.iot.server.service.util.PermTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionAbility permissionAbility;

    @Override
    public Boolean createPermission(String spaceId, PermissionCreateReq permissionCreateRequest) {
        return permissionAbility.createPermission(spaceId, permissionCreateRequest);
    }

    @Override
    public Boolean batchCreatePermission(String spaceId, List<PermissionCreateReq> permissionCreateRequestList) {
        return permissionAbility.batchCreatePermission(spaceId,
                PermissionBatchCreateReq.builder().permissionList(permissionCreateRequestList).build()
        );
    }

    @Override
    public Boolean updatePermission(String spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest) {
        return permissionAbility.updatePermission(spaceId, permissionCode, permissionUpdateRequest);
    }

    @Override
    public Boolean deletePermission(String spaceId, String permissionCode) {
        return permissionAbility.deletePermission(spaceId, permissionCode);
    }

    @Override
    public IdaasPermission getPermissionByCode(String spaceId, String permissionCode) {
        return permissionAbility.getPermissionByCode(spaceId, permissionCode);
    }

    @Override
    public List<IdaasPermission> queryPermissionsByCodes(String spaceId,List<String> permCodes) {
        return permissionAbility.queryPermissionsByCodes(spaceId, PermissionQueryReq.builder().permissionCodeList(permCodes).build());
    }

    @Override
    public List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(String spaceId, PermissionQueryByRolesReq request) {
        return permissionAbility.queryPermissionsByRoleCodes(spaceId,request);
    }

    @Override
    public List<IdaasPermission> queryPermissionsByUser(String spaceId, String uid) {
        return permissionAbility.queryPermissionsByUser(spaceId, uid);
    }

    @Override
    public List<PermissionNodeDTO> queryPermissionTrees(String permissionSpaceId, String uid) {
        List<PermissionNodeDTO> perms = permissionAbility.queryPermissionsByUser(permissionSpaceId, uid)
                .stream()
                .map(it ->
                        PermissionNodeDTO.builder()
                                .permissionCode(it.getPermissionCode())
                                .permissionName(it.getName())
                                .permissionType(PermissionTypeEnum.fromCode(it.getType()).name())
                                .remark(it.getRemark())
                                .order(it.getOrder())
                                .parentCode(it.getParentCode())
                                .build())
                .collect(Collectors.toList());
        return PermTemplateUtil.buildTrees(perms);
    }
}
