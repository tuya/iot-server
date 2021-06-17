package com.tuya.iot.suite.ability

import com.google.common.hash.Hashing
import com.tuya.connector.open.common.util.Sha256Util
import com.tuya.iot.suite.ability.user.ability.UserAbility
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.nio.charset.StandardCharsets

/**
 * @description 该测试用例依赖日常环境* @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
class UserAbilitySpec extends BaseSpec {
    @Autowired
    UserAbility userAbility
    @Autowired
    ProjectProperties projectProperties

    void "测试用户登陆"() {
        expect:
        userAbility.loginUser(UserRegisteredRequest.builder()
                .username(projectProperties.adminUserName)
                .password(Sha256Util.encryption(projectProperties.adminUserPwd))
                .build())
    }

    void "测试查询用户"() {
        expect:
        userAbility.selectUser('bsh1623052900346u8pQ')
    }

    void "测试注册用户"(){
        when:
        def res = userAbility.registeredUser(UserRegisteredRequest.builder()
                .username('xxx')
                .password(Hashing.sha256().hashString("xxx", StandardCharsets.UTF_8).toString())
                .country_code("86")
                .build()
        )
        then:
        noExceptionThrown()
    }

}
