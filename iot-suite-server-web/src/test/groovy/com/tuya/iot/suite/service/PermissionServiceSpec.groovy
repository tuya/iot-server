package com.tuya.iot.suite.service

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum
import com.tuya.iot.suite.service.idaas.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.ResourceUtils
import org.springframework.util.StreamUtils

import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
class PermissionServiceSpec extends BaseSpec{
    @Autowired
    PermissionService permissionService
    void "测试查询我的权限树"(){
        given:
        def permAbility = Mock(PermissionAbility)
        permissionService.permissionAbility = permAbility
        permAbility.queryPermissionsByUser(_,_) >> loadAllPerms()

        def spaceId = 1000
        def uid = "1000"
        when:
        def trees = permissionService.queryPermissionTrees(spaceId, uid)
        then:
        trees.size() == 5
    }
    private List<IdaasPermission> loadAllPerms() {
        File file = ResourceUtils.getFile("classpath:permissions.json");
        String json = StreamUtils.copyToString(new FileInputStream(file), StandardCharsets.UTF_8);
        JSONObject root = JSONObject.parseObject(json);
        JSONArray menuPerms = root.getJSONArray("permissionList");
        return menuPerms.stream().flatMap({menuPermObj->
            JSONObject menuPerm = (JSONObject) menuPermObj;
            def perms = menuPerm.getJSONArray("permissionList")
            perms.add(menuPerm)
            return perms.stream();
        }).map( {perm->
            JSONObject it = (JSONObject) perm;
            return IdaasPermission.builder()
                    .parentCode(it.getString("parentCode"))
                    .permissionCode(it.getString("permissionCode"))
                    .name(it.getString("permissionName"))
                    .type(PermissionTypeEnum.valueOf(it.getString("type")))
                    .order(it.getInteger("order"))
                    .remark(it.getString("remark"))
                    .build();
        }).collect(Collectors.toList());
    }
}
