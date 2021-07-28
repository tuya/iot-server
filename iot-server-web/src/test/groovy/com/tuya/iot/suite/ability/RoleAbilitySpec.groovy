package com.tuya.iot.suite.ability

import com.tuya.iot.server.ability.idaas.ability.RoleAbility
import com.tuya.iot.server.ability.idaas.model.IdaasRole
import com.tuya.iot.server.ability.idaas.model.IdaasRoleCreateReq
import com.tuya.iot.server.ability.idaas.model.RoleUpdateReq
import com.tuya.iot.server.ability.idaas.model.RolesPaginationQueryReq
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.server.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Stepwise

/**
 * @description 该测试用例依赖日常环境* @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
//@Stepwise
class RoleAbilitySpec extends BaseSpec {
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

    void "测试查询角色byCode"() {
        when:
        IdaasRole role = roleAbility.getRole(spaceId,'admin')
        then:
        role.roleCode == 'admin'
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
        .gmtModifiedAsc(true)
                .roleCodes(['admin','normal-1000'])
                .build())
        then:
        page.totalCount == 1

    }

    void "测试删除角色"() {
        expect:
        roleAbility.deleteRole(spaceId,'admin')
    }
}
