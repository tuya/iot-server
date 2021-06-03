package com.tuya.iot.suite.service

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum
import com.tuya.iot.suite.service.idaas.PermissionService
import com.tuya.iot.suite.service.util.PermTemplateUtil
import groovy.util.logging.Slf4j
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
@Slf4j
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
        log.info(JSON.toJSONString(trees,true))
        then:
        trees.size() == 5
    }
    private List<IdaasPermission> loadAllPerms() {
        PermTemplateUtil.loadAsList("classpath:template/permissions-admin.json")
        .collect{
            it->IdaasPermission.builder()
            .name(it.permissionName)
            .parentCode(it.parentCode)
            .type(PermissionTypeEnum.valueOf(it.permissionType))
            .remark(it.remark)
            .order(it.order)
            .permissionCode(it.permissionCode)
                    .build()
        }
    }
}
