package com.tuya.iot.suite.ability

import com.tuya.iot.suite.ability.idaas.ability.PermissionAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasPermission
import com.tuya.iot.suite.ability.idaas.model.PermissionBatchCreateReq
import com.tuya.iot.suite.ability.idaas.model.PermissionCreateReq
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryReq
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum
import com.tuya.iot.suite.ability.idaas.model.PermissionUpdateReq
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.test.Env
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Timeout

/**
 * @description
 * 字段名命名风格：我们这边会转成下划线风格传过去，然后网关那边处理了，两种都支持。
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
class PermissionAbilitySpec extends BaseSpec {
    static {
        Env.useDailyCn()
    }
    @Autowired
    PermissionAbility permissionAbility
    @Autowired
    ProjectProperties projectProperties

    long spaceId

    void setup() {
        spaceId = projectProperties.getPermissionSpaceId()
    }

    void "测试新增权限"() {
        given:

        when:
        //已经添加过的再添加返回{"code":501,"msg":"request fail with unkown error","success":false,"t":1623119178514}
        def success = permissionAbility.createPermission(spaceId, PermissionCreateReq.builder()
                .name('app')
                .order(1)
                .type(PermissionTypeEnum.menu.code)
                .permissionCode('app')
                .remark("应用")
                .build()
        )
        then:
        success
    }

    void "测试根据权限code查询权限"() {
        given:
        when:
        IdaasPermission permission = permissionAbility.getPermissionByCode(spaceId, 'app')
        then:
        permission.permissionCode == 'app'
    }

    void "测试批量查询权限"() {
        given:
        when:
        List<IdaasPermission> permissions = permissionAbility.queryPermissionsByCodes(spaceId, PermissionQueryReq.builder()
                .spaceId(spaceId)
                .permissionCodeList(['app'])
                .build())
        then:
        permissions.size() == 1
    }

    /**
     * 踩坑记：
     发现一个坑，调openapi接口的框架里面，
     com.tuya.connector.api.core.delegate.RetrofitDelegate#getGlobalRetrofit有一行代码
     gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
     将驼峰转下划线了。
     之前对方是使用下划线风格接收的。
     现在他们一个新的系统用的驼峰风格的。
     * */
    void "测试批量创建权限"() {
        given:
        when:
        def success = permissionAbility.batchCreatePermission(spaceId, PermissionBatchCreateReq.builder()
                .permissionList([PermissionCreateReq.builder()
                                            .permissionCode('role-menu')
                                            .type(PermissionTypeEnum.menu.code)
                                            .name('role-menu')
                                            .parentCode('app')
                                            .order(5)
                                            .build(),
                                 PermissionCreateReq.builder()
                                            .permissionCode('user-menu')
                                            .type(PermissionTypeEnum.menu.code)
                                            .name('user-menu')
                                            .parentCode('app')
                                            .order(6)
                                            .build()])
                .build()
        )
        then:
        success
    }

    @Timeout(1000)
    void "测试更新权限"() {
        given:
        expect:
        permissionAbility.updatePermission(spaceId, 'app', PermissionUpdateReq.builder()
                .remark("my-app")
                .order(100)
                .name("yo-app")
                .build())
    }

    @Timeout(1000)
    void "测试删除权限"() {
        given:
        expect:
        permissionAbility.deletePermission(spaceId, 'role-menu')
        permissionAbility.deletePermission(spaceId, 'user-menu')
    }

    @Timeout(1000)
    void "根据角色查询权限"() {
        given:
        expect:
        permissionAbility.queryPermissionsByUser(spaceId, 'uid')
    }

    @Timeout(1000)
    void "根据用户查询权限"() {
        given:
        expect:
        permissionAbility.queryPermissionsByRoleCodes(spaceId, [])
    }
}
