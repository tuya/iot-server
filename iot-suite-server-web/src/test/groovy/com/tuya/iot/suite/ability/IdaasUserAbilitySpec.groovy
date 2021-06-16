package com.tuya.iot.suite.ability

import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasUserCreateReq
import com.tuya.iot.suite.ability.idaas.model.IdaasUserPageReq
import com.tuya.iot.suite.ability.idaas.model.IdaasUserUpdateReq
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

/**
 * @description 该测试用例依赖日常环境* @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
//@Stepwise//使用该注解，测试按声明顺序执行
class IdaasUserAbilitySpec extends BaseSpec {
    @Autowired
    IdaasUserAbility idaasUserAbility
    @Autowired
    ProjectProperties projectProperties
    String spaceId

    void setup() {
        spaceId = projectProperties.getPermissionSpaceId()
    }

    void "测试新增用户"() {
        expect:
        idaasUserAbility.createUser(spaceId, IdaasUserCreateReq.builder()
                .uid('bsh1623052900346u8pQ')
                .username('benguan')
                .build())
    }


    void "测试修改用户"() {
        expect:
        idaasUserAbility.updateUser(spaceId, 'bsh1623052900346u8pQ', IdaasUserUpdateReq.builder()
                .username('benguan.zhou')
                .build())
    }


    void "测试查询用户byUid"() {
        expect:
        idaasUserAbility.getUserByUid(spaceId, 'bsh1623052900346u8pQ').username == 'benguan.zhou'
    }

    void "测试查询用户分页"() {
        when:
        idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder()
                .roleCode('admin')
        .pageNumber(2)
        .pageSize(2)
                .build())
        then:
        noExceptionThrown()
    }

    void "测试删除用户"() {
        expect:
        idaasUserAbility.deleteUser(spaceId, 'bsh1623052900346u8pQ')
    }
}
