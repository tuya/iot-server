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
     * roleType=>permissionTemplate
     * <p>
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
    public Set<String> getAuthorizablePermissions() {
        return rolePermissionsMapRef.get().get(RoleTypeEnum.admin.name())
                .stream().filter(it->it.getAuthorizable()).map(it->it.getPermissionCode())
                .collect(Collectors.toSet());
    }


    @Override
    public PermissionNodeDTO getPermissionTemplate(String roleTypeOrRoleCode) {
        return rolePermissionTmplMapRef.get().get(RoleTypeEnum.fromRoleCode(roleTypeOrRoleCode));
    }


}
