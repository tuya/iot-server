package com.tuya.iot.suite.ability

import com.tuya.iot.server.ability.idaas.ability.GrantAbility
import com.tuya.iot.server.ability.idaas.model.RoleGrantPermissionReq
import com.tuya.iot.server.ability.idaas.model.RoleGrantPermissionsReq
import com.tuya.iot.server.ability.idaas.model.RoleRevokePermissionsReq
import com.tuya.iot.server.ability.idaas.model.UserGrantRoleReq
import com.tuya.iot.server.ability.idaas.model.UserGrantRolesReq
import com.tuya.iot.server.ability.idaas.model.UserRevokeRolesReq
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.server.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

/**
 * @description 该测试用例依赖日常环境* @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
class GrantAbilitySpec extends BaseSpec {
    @Autowired
    GrantAbility grantAbility
    @Autowired
    ProjectProperties projectProperties
    String spaceId

    void setup() {
        spaceId = projectProperties.getPermissionSpaceId()
    }

    void "测试角色授权"() {
        when:
        def success = grantAbility.grantPermissionToRole(RoleGrantPermissionReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCode('app')
                .build())
        then:
        success
    }

    void "测试角色批量授权"() {
        when:
        def success = grantAbility.grantPermissionsToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCodes(['app'])
                .build())
        then:
        success
    }

    void "测试设置角色权限"() {
        expect:
        grantAbility.setPermissionsToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCodes(['app','1000'])
                .build())
    }

    void "测试回收角色权限"() {
        when:
        def success = grantAbility.revokePermissionFromRole(spaceId, '1000', 'admin')
        then:
        success
    }

    void "测试批量回收角色权限"() {
        expect:
        grantAbility.revokePermissionsFromRole(RoleRevokePermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCodes(['app'])
                .build())
    }

    void "测试授予用户角色"() {
        expect:
        grantAbility.grantRoleToUser(UserGrantRoleReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .uid('bsh1623052900346u8pQ')
                .build())
    }

    void "测试设置用户角色"() {
        expect:
        grantAbility.setRolesToUser(UserGrantRolesReq.builder()
                .spaceId(spaceId)
                .roleCodeList(['admin'])
                .uid('bsh1623052900346u8pQ')
                .build())
    }

    void "测试回收用户角色"() {
        expect:
        grantAbility.revokeRoleFromUser(spaceId, 'admin', 'bsh1623052900346u8pQ')
    }

    void "测试批量回收用户角色"() {
        when:
        def success = grantAbility.revokeRolesFromUser(UserRevokeRolesReq.builder()
                .spaceId(spaceId)
                .uid('bsh1623052900346u8pQ')
                .roleCodeList(['admin'])
                .build())
        then:
        success
    }
}
