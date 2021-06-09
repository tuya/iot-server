package com.tuya.iot.suite.service

import com.alibaba.fastjson.JSON
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum
import com.tuya.iot.suite.service.idaas.PermissionService
import com.tuya.iot.suite.service.idaas.PermissionTemplateService
import com.tuya.iot.suite.service.util.PermTemplateUtil
import com.tuya.iot.suite.test.Success
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/09
 */
@Slf4j
class PermissionTemplateServiceSpec extends BaseSpec{
    @Autowired
    PermissionTemplateService permissionTemplateService
    void "测试查询可授权权限"(){
        given:

        when:
        def perms = permissionTemplateService.getAuthorizablePermissions()
        log.info(JSON.toJSONString(perms))
        then:
        perms.size()>0
    }

    void "测试查询模版权限树"(){
        given:

        when:
        def perms = permissionTemplateService.getTemplatePermissionTrees(roleType,lang)
        log.info(JSON.toJSONString(perms))
        throw new Success()
        then:
        thrown(exType)
        where:
        roleType|lang|exType
        'admin'|'zh'|Success
        'admin'|'en'|Success
        'normal'|'ab'|Success
    }
}
