package com.tuya.iot.suite.service

import com.tuya.iot.suite.ability.idaas.ability.GrantAbility
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasRole
import com.tuya.iot.suite.core.exception.ServiceLogicException
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO
import com.tuya.iot.suite.service.idaas.RoleService
import com.tuya.iot.suite.service.model.RoleCodeGenerator
import com.tuya.iot.suite.service.model.RoleTypeEnum
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
@Slf4j
class RoleServiceSpec extends BaseSpec {

    @Autowired
    RoleService roleService

    void "测试创建角色-创建成功"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('admin')
                                                       .roleCode("admin")
                                                       .build()
        ]
        //roleAbility.createRole(_, _) >> true
        roleAbility.createRole(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}",req)
                true
        }

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.manage,"1000"))
                .roleName('manage1000')
                .build())
        then:
        createResult
    }


    void "测试创建角色-创建失败-没有权限"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('normal')
                                                       .roleCode("normal-1000")
                                                       .build()
        ]
        //roleAbility.createRole(_, _) >> true
        roleAbility.createRole(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}",req)
                true
        }

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.manage,"1000"))
                .roleName('manage1000')
                .build())
        then:
        thrown(ServiceLogicException)
    }

    void "测试创建角色-创建失败-不能创建管理员"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('admin')
                                                       .roleCode("admin")
                                                       .build()
        ]
        //roleAbility.createRole(_, _) >> true
        roleAbility.createRole(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}",req)
                true
        }

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.admin,"1000"))
                .roleName('admin1000')
                .build())
        then:
        thrown(IllegalArgumentException)
    }
}
