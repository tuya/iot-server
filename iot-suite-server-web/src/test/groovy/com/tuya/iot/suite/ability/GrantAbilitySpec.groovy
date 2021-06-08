package com.tuya.iot.suite.ability

import com.tuya.iot.suite.ability.idaas.ability.GrantAbility
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionReq
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq
import com.tuya.iot.suite.ability.idaas.model.RoleRevokePermissionsReq
import com.tuya.iot.suite.ability.idaas.model.UserGrantRoleReq
import com.tuya.iot.suite.ability.idaas.model.UserRevokeRolesReq
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.test.Env
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore

/**
 * @description 该测试用例依赖日常环境* @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
class GrantAbilitySpec extends BaseSpec {
    static {
        Env.useDailyCn()
    }
    @Autowired
    GrantAbility grantAbility
    @Autowired
    ProjectProperties projectProperties
    long spaceId

    void setup() {
        spaceId = projectProperties.getPermissionSpaceId()
    }

    void "测试角色授权"() {
        expect:
        grantAbility.grantPermissionToRole(RoleGrantPermissionReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCode('app')
                .build())
    }

    void "测试角色批量授权"() {
        expect:
        grantAbility.grantPermissionToRole(RoleGrantPermissionsReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCodes(['app'])
                .build())
    }

    void "测试设置角色权限"() {
        expect:
        grantAbility.setPermissionsToRole(RoleGrantPermissionReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .permissionCode('app')
                .build())
    }

    void "测试回收角色权限"() {
        expect:
        grantAbility.revokePermissionFromRole(spaceId, 'app', 'admin')
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
                .uid('todo')
                .build())
    }

    void "测试设置用户角色"() {
        expect:
        grantAbility.setRolesToUser(UserGrantRoleReq.builder()
                .spaceId(spaceId)
                .roleCode('admin')
                .uid('todo')
                .build())
    }

    void "测试回收用户角色"() {
        expect:
        grantAbility.revokeRoleFromUser(spaceId, 'admin', 'todo')
    }

    void "测试批量回收用户角色"() {
        expect:
        grantAbility.revokeRolesFromUser(UserRevokeRolesReq.builder()
                .spaceId(spaceId)
                .uid('todo')
                .roleCodeList(['app'])
                .build())
    }
}
