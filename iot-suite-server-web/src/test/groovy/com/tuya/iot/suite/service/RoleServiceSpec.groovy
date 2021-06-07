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
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO
import com.tuya.iot.suite.service.idaas.RoleService
import com.tuya.iot.suite.test.Success
import com.tuya.iot.suite.web.config.ProjectProperties
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
    ProjectProperties projectProperties
    @Autowired
    RoleService roleService

    void "测试创建角色"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName(operatorRoleName)
                                                       .roleCode(operatorRoleCode)
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
        log.info("测试场景：{}",label)
        def createResult = roleService.createRole(spaceId, RoleCreateReqDTO.builder()
                .roleCode(roleCode)
                .roleName(roleName)
                .build())
        assert createResult
        throw new Success()
        then:
        thrown(expectedException)

        where:
        label|operatorRoleCode|operatorRoleName|roleCode||roleName||expectedException
        '创建成功'|'admin'|'admin'|'manage-1000'|'manage1000'|| Success
        '没有权限'|'normal'|'normal-1000'|'manage-1000'|'manage1000'||RuntimeException
        '不能创建管理员'|'admin'|'admin'|'admin-1000'|'admin1000'||RuntimeException
    }

    void "测试删除角色"() {
        given:
        def roleAbility = Mock(RoleAbility)
        def grantAbility = Mock(GrantAbility)
        def idaasUserAbility = Mock(IdaasUserAbility)
        roleService.roleAbility = roleAbility
        roleService.grantAbility = grantAbility
        roleService.idaasUserAbility = idaasUserAbility
        roleAbility.queryRolesByUser(_, _) >> [IdaasRole.builder()
                                                       .roleName(operatorRoleName)
                                                       .roleCode(operatorRoleCode)
                                                       .build()
        ]
        roleAbility.deleteRole(_, _) >> {
            spaceId, roleCode0 ->
                log.info("spaceId=$spaceId,roleCode=$roleCode0")
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
                pageResult
        }
        def spaceId = 1000

        when:
        log.info("测试场景：{}",label)
        def result = roleService.deleteRole(spaceId, operatorUid, roleCode)
        assert result
        throw new Success()

        then:
        thrown(expectedException)
        where:
        label|operatorRoleCode|operatorRoleName|operatorUid|roleCode|pageResult||expectedException
        '不能删除系统管理员'|'admin'|'admin'|'u123456'|'admin'|IdaasPageResult.builder().build()||RuntimeException
        '存在关联的用户'|'admin'|'admin'|'u123456'|'admin'|IdaasPageResult.builder()
                .totalCount(1)
                .results([IdaasUser.builder().username('monkey').build()])
                .build()||RuntimeException
        '权限不足'|'normal-1000'|'normal1000'|'u123456'|'manage-1000'|IdaasPageResult.builder()
                .totalCount(0)
                .results([])
                .build()||RuntimeException
        '成功'|'admin'|'admin'|'u123456'|'manage-1000'|IdaasPageResult.builder()
                .totalCount(0)
                .results([])
                .build()||Success
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
