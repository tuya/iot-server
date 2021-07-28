package com.tuya.iot.suite.ability

import com.tuya.iot.server.ability.idaas.ability.PermissionCheckAbility
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.server.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Stepwise

/**
 * @description 该测试用例依赖日常环境
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
@Stepwise
class PermissionCheckerAbilitySpec extends BaseSpec{
    @Autowired
    PermissionCheckAbility permissionCheckAbility
    @Autowired
    ProjectProperties projectProperties

    String spaceId

    void setup() {
        spaceId = projectProperties.getPermissionSpaceId()
    }

    void "测试校验角色权限"(){
        when:
        permissionCheckAbility.checkPermissionForRole(spaceId,'app','admin')
        then:
        noExceptionThrown()
    }

    void "测试校验用户角色"(){
        when:
        permissionCheckAbility.checkRoleForUser(spaceId,'admin','bsh1623052900346u8pQ')
        then:
        noExceptionThrown()
    }
    void "测试校验用户权限"(){
        when:
        permissionCheckAbility.checkPermissionForUser(spaceId,'app','bsh1623052900346u8pQ')
        then:
        noExceptionThrown()
    }

}
