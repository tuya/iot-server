package com.tuya.iot.suite.service

import com.tuya.iot.suite.ability.idaas.ability.GrantAbility
import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility
import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasPageResult
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission
import com.tuya.iot.suite.ability.idaas.model.IdaasRole
import com.tuya.iot.suite.ability.idaas.model.IdaasUser
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesRespItem
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
                log.info("grant===>{}", req)
                true
        }

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.manage, "1000"))
                .roleName('manage1000')
                .build())
        then:
        createResult
    }


    void "测试创建角色-没有权限"() {
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
                log.info("grant===>{}", req)
                true
        }

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.manage, "1000"))
                .roleName('manage1000')
                .build())
        then:
        thrown(ServiceLogicException)
    }

    void "测试创建角色-不能创建系统管理员"() {
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
                log.info("grant===>{}", req)
                true
        }

        def spaceId = 1000
        when:
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(RoleTypeEnum.admin, "1000"))
                .roleName('admin1000')
                .build())
        then:
        thrown(IllegalArgumentException)
    }


    void "测试删除角色-不能删除系统管理员"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        def idaasUserAbility = Mock(IdaasUserAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleService.idaasUserAbility = idaasUserAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('admin')
                                                       .roleCode("admin")
                                                       .build()
        ]
        roleAbility.deleteRole(_, _) >> {
            spaceId, roleCode ->
                log.info("spaceId=$spaceId,roleCode=$roleCode")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}", req)
                true
        }
        idaasUserAbility.queryUserPage(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                IdaasPageResult.builder().build()
        }

        def spaceId = 1000
        when:
        def result = roleService.deleteRole(spaceId, "u123456", "admin")
        then:
        thrown(IllegalArgumentException)
    }

    void "测试删除角色-存在关联的用户"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        def idaasUserAbility = Mock(IdaasUserAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleService.idaasUserAbility = idaasUserAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('admin')
                                                       .roleCode("admin")
                                                       .build()
        ]
        roleAbility.deleteRole(_, _) >> {
            spaceId, roleCode ->
                log.info("spaceId=$spaceId,roleCode=$roleCode")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}", req)
                true
        }
        idaasUserAbility.queryUserPage(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                IdaasPageResult.builder()
                        .totalCount(1)
                        .results([IdaasUser.builder().username('monkey').build()])
                        .build()
        }

        def spaceId = 1000
        when:
        def result = roleService.deleteRole(spaceId, "u123456", "manage-1000")
        then:
        thrown(ServiceLogicException)
    }

    void "测试删除角色-权限不足"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        def idaasUserAbility = Mock(IdaasUserAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleService.idaasUserAbility = idaasUserAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('normal1000')
                                                       .roleCode("normal-1000")
                                                       .build()
        ]
        roleAbility.deleteRole(_, _) >> {
            spaceId, roleCode ->
                log.info("spaceId=$spaceId,roleCode=$roleCode")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}", req)
                true
        }
        idaasUserAbility.queryUserPage(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                IdaasPageResult.builder()
                        .totalCount(0)
                        .results([])
                        .build()
        }

        def spaceId = 1000
        when:
        def result = roleService.deleteRole(spaceId, "u123456", "manage-1000")
        then:
        thrown(ServiceLogicException)
    }

    void "测试删除角色-成功"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        def idaasUserAbility = Mock(IdaasUserAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleService.idaasUserAbility = idaasUserAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('admin')
                                                       .roleCode("admin")
                                                       .build()
        ]
        roleAbility.deleteRole(_, _) >> {
            spaceId, roleCode ->
                log.info("spaceId=$spaceId,roleCode=$roleCode")
                true
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}", req)
                true
        }
        idaasUserAbility.queryUserPage(_, _) >> {
            spaceId, req ->
                log.info("spaceId=$spaceId,req=$req")
                IdaasPageResult.builder()
                        .totalCount(0)
                        .results([])
                        .build()
        }

        def spaceId = 1000
        when:
        def result = roleService.deleteRole(spaceId, "u123456", "manage-1000")
        then:
        result
    }

    void "重新设置角色权限-成功"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        def idaasUserAbility = Mock(IdaasUserAbility)
        def permissionAbility = Mock(PermissionAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleService.idaasUserAbility = idaasUserAbility
        roleService.permissionAbility = permissionAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName('admin')
                                                       .roleCode("admin")
                                                       .build()
        ]
        permissionAbility.queryPermissionsByRoleCodes(_) >> {
            req ->
                log.info("req=$req")
                [PermissionQueryByRolesRespItem.builder()
                         .roleCode("admin")
                         .permissionList([IdaasPermission.builder()
                                                  .permissionCode("1000")
                                                  .build(),
                                          IdaasPermission.builder()
                                                  .permissionCode("9001")
                                                  .build()
                         ])
                         .build()
                ]
        }
        grantAbility.grantPermissionsToRole(_) >> {
            req ->
                log.info("grant===>{}", req)
                true
        }
        grantAbility.revokePermissionsFromRole(_) >> {
            req ->
                log.info("revoke===>{}",req)
                true
        }

        def spaceId = 1000
        when:
        def result = roleService.resetRolePermissionsFromTemplate(spaceId, "u123456", "manage-1000")
        then:
        result
    }
}
