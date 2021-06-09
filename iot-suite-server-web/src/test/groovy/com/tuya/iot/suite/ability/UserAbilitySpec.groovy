package com.tuya.iot.suite.ability

import com.tuya.connector.open.common.util.Sha256Util
import com.tuya.iot.suite.ability.user.ability.UserAbility
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest
import com.tuya.iot.suite.service.BaseSpec
import com.tuya.iot.suite.web.config.ProjectProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

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
                .username('18820077637')
                .password(Sha256Util.encryption('ty1198094110'))
                .build())
    }

    void "测试查询用户"() {
        expect:
        userAbility.selectUser('bsh1623052900346u8pQ')
    }

}
