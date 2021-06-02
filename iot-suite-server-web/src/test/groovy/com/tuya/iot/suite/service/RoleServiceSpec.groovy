package com.tuya.iot.suite.service

import com.tuya.iot.suite.ability.idaas.ability.RoleAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasRole
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO
import com.tuya.iot.suite.service.idaas.RoleService
import com.tuya.iot.suite.service.model.RoleCodeGenerator
import com.tuya.iot.suite.service.model.RoleTypeEnum
import org.springframework.beans.factory.annotation.Autowired

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */

class RoleServiceSpec extends BaseSpec {

    @Autowired
    RoleService roleService

    void "测试创建角色"() {
        given:
        def roleAbility = Mock(RoleAbility)
        roleService.roleAbility = roleAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('test')
                                                       .roleCode("admin")
                                                       .build()
        ]
        roleAbility.createRole(_, _) >> true

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.manage))
                .roleName('sysadmin1000')
                .build())
        then:
        createResult
    }

}
