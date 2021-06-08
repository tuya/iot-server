package com.tuya.iot.suite.ability

import com.tuya.iot.suite.ability.idaas.ability.PermissionCheckAbility
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasRole
import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq
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
class RoleAbilitySpec extends BaseSpec {
    static {
        Env.useDailyCn()
    }
    @Autowired
    RoleAbility roleAbility
    @Autowired
    ProjectProperties projectProperties

    String spaceId

    void setup() {
        spaceId = projectProperties.getPermissionSpaceId()
    }

    void "测试创建角色"() {
        def success = roleAbility.createRole(spaceId, IdaasRoleCreateReq.builder()
                .roleCode('admin')
                .roleName('administrator')
                .build())
        expect:
        success
    }

    void "测试更新角色"() {
        expect:
        roleAbility.updateRole(spaceId, 'admin', RoleUpdateReq.builder()
                .roleName('系统管理员')
                .build())
    }

    void "测试删除角色"() {
        expect:
        roleAbility.deleteRole(spaceId,'admin')
    }

    void "测试查询角色byCode"() {
        when:
        IdaasRole role = roleAbility.getRole(spaceId,'admin')
        then:
        role.roleCode == 'amdin'
    }

    void "测试查询角色byUser"() {
        when:
        def perms = roleAbility.queryRolesByUser(spaceId,'bsh1623052900346u8pQ')
        then:
        perms.size() == 0
    }

    void "测试查询角色分页"() {
        when:
        def page = roleAbility.queryRolesPagination(spaceId, RolesPaginationQueryReq.builder()
                .roleCodes(['admin'])
                .build())
        then:
        page.totalCount == 1

    }
}
