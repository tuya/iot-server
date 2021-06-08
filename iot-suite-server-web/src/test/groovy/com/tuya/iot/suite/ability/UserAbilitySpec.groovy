package com.tuya.iot.suite.ability

import com.tuya.connector.open.common.util.Sha256Util
import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility
import com.tuya.iot.suite.ability.idaas.model.IdaasUserCreateReq
import com.tuya.iot.suite.ability.idaas.model.IdaasUserPageReq
import com.tuya.iot.suite.ability.idaas.model.IdaasUserUpdateReq
import com.tuya.iot.suite.ability.user.ability.UserAbility
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest
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
class UserAbilitySpec extends BaseSpec {
    static {
        Env.useDailyCn()
    }
    @Autowired
    UserAbility userAbility
    @Autowired
    ProjectProperties projectProperties

    void "测试用户登陆"() {
        expect:
        userAbility.loginUser(UserRegisteredRequest.builder()
                .username('xxx')
                .password(Sha256Util.encryption('xxx'))
                .build())
    }

}
