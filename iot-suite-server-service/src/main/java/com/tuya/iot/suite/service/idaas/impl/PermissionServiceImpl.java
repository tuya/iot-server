package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility;
import com.tuya.iot.suite.ability.idaas.model.*;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.idaas.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public Boolean createPermission(Long spaceId, PermissionCreateReq permissionCreateRequest) {
        return permissionAbility.createPermission(spaceId, permissionCreateRequest);
    }

    @Override
    public Boolean batchCreatePermission(Long spaceId, List<PermissionCreateReq> permissionCreateRequestList) {
        return permissionAbility.batchCreatePermission(spaceId,
                PermissionBatchCreateReq.builder().permissionList(permissionCreateRequestList).build()
        );
    }

    @Override
    public Boolean updatePermission(Long spaceId, String permissionCode, PermissionUpdateReq permissionUpdateRequest) {
        return permissionAbility.updatePermission(spaceId, permissionCode, permissionUpdateRequest);
    }

    @Override
    public Boolean deletePermission(Long spaceId, String permissionCode) {
        return permissionAbility.deletePermission(spaceId, permissionCode);
    }

    @Override
    public IdaasPermission getPermissionByCode(Long spaceId, String permissionCode) {
        return permissionAbility.getPermissionByCode(spaceId, permissionCode);
    }

    @Override
    public List<IdaasPermission> queryPermissionsByCodes(Long spaceId,List<String> permCodes) {
        return permissionAbility.queryPermissionsByCodes(spaceId,PermissionQueryReq.builder().permissionCodeList(permCodes).build());
    }

    @Override
    public List<PermissionQueryByRolesRespItem> queryPermissionsByRoleCodes(Long spaceId,PermissionQueryByRolesReq request) {
        return permissionAbility.queryPermissionsByRoleCodes(spaceId,request);
    }

    @Override
    public List<IdaasPermission> queryPermissionsByUser(Long spaceId, String uid) {
        return permissionAbility.queryPermissionsByUser(spaceId, uid);
    }

    @Override
    public List<PermissionNodeDTO> queryPermissionTrees(Long permissionSpaceId, String uid) {
        List<PermissionNodeDTO> perms = permissionAbility.queryPermissionsByUser(permissionSpaceId, uid)
                .stream()
                .map(it ->
                        PermissionNodeDTO.builder()
                                .permissionCode(it.getPermissionCode())
                                .permissionName(it.getName())
                                .permissionType(it.getType().name())
                                .remark(it.getRemark())
                                .order(it.getOrder())
                                .parentCode(it.getParentCode())
                                .build())
                .collect(Collectors.toList());
        //permissionCode=>PermissionNodeDTO
        Map<String, PermissionNodeDTO> map = perms.stream().collect(Collectors.toMap(it -> it.getPermissionCode(), it -> it));
        //permissionCode=>children
        Map<String, List<PermissionNodeDTO>> childrenMap = perms.stream().collect(Collectors.groupingBy(it -> it.getParentCode()));
        //find roots, which parentCode not in map
        List<PermissionNodeDTO> trees = perms.stream().filter(it -> !map.containsKey(it.getParentCode())).collect(Collectors.toList());
        //set children
        perms.forEach(it -> it.setChildren(childrenMap.get(it.getPermissionCode())));
        return trees;
    }
}
