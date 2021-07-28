package com.tuya.iot.suite.service

import com.google.common.hash.Hashing
import com.tuya.iot.server.ability.user.model.UserRegisteredRequest
import com.tuya.iot.server.service.user.UserService
import com.tuya.iot.server.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.nio.charset.StandardCharsets

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
@Slf4j
class UserServiceSpec extends BaseSpec {

    @Autowired
    ProjectProperties projectProperties
    @Autowired
    UserService userService

    void "测试创建用户"() {
        when:
        def res = userService.createUser(projectProperties.permissionSpaceId, UserRegisteredRequest.builder()
                .username('15262900902')
        .password(Hashing.sha256().hashString('ty1198094110', StandardCharsets.UTF_8).toString())
        .country_code('86')
                .build(),['normal-1000'])
        then:
        noExceptionThrown()

    }

    void "测试删除用户"() {
        when:
        def res = userService.deleteUser(projectProperties.permissionSpaceId,'bsh1623898588066Dx8a')
        then:
        noExceptionThrown()

    }

}
