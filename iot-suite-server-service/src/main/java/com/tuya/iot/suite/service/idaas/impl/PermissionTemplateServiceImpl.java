package com.tuya.iot.suite.service.idaas.impl;

import com.tuya.iot.suite.core.util.LazyRef;
import com.tuya.iot.suite.core.util.Tuple2;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.idaas.PermissionTemplateService;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import com.tuya.iot.suite.service.util.PermTemplateUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/03
 */
@Service
public class PermissionTemplateServiceImpl implements PermissionTemplateService {

    /**
     * roleType=>permission trees
     * <p>
     * load only when first call
     */
    private LazyRef<Map<String, List<PermissionNodeDTO>>> permTreesMapHolder = LazyRef.lateInit(() ->
            Stream.of(RoleTypeEnum.values()).map(roleType ->
                    new Tuple2<>(roleType.name(), PermTemplateUtil
                            .loadTrees("classpath:template/permissions.json",node->node.getAuthRoleTypes().contains(roleType.name())))
            ).collect(Collectors.toMap(it -> it.first(), it -> it.second()))
    );

    /**
     * roleType=>permission flatten list
     */
    private LazyRef<Map<String, List<PermissionNodeDTO>>> permFlattenListMapHolder = LazyRef.lateInit(() ->
            Stream.of(RoleTypeEnum.values()).map(it ->
                    new Tuple2<>(it.name(), PermTemplateUtil
                            .loadAsFlattenList("classpath:template/permissions.json",node->node.getAuthRoleTypes().contains(it.name())))
            ).collect(Collectors.toMap(it -> it.first(), it -> it.second()))
    );

    @Override
    public Set<String> getAuthorizablePermissions() {
        return permFlattenListMapHolder.get().get(RoleTypeEnum.admin.name())
                .stream().filter(it->it.getAuthorizable()).map(it->it.getPermissionCode())
                .collect(Collectors.toSet());
    }


    @Override
    public List<PermissionNodeDTO> getTemplatePermissionTrees(String roleTypeOrRoleCode) {
        return permTreesMapHolder.get().get(RoleTypeEnum.fromRoleCode(roleTypeOrRoleCode).name());
    }

    @Override
    public List<PermissionNodeDTO> getTemplatePermissionFlattenList(String roleType) {
        return permFlattenListMapHolder.get().get(roleType);
    }


}
