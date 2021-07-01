package com.tuya.iot.suite.web

import com.alibaba.fastjson.JSON
import com.tuya.connector.open.common.util.Sha256Util
import com.tuya.iot.suite.ability.user.ability.UserAbility
import com.tuya.iot.suite.ability.user.model.UserToken
import com.tuya.iot.suite.service.user.UserService
import com.tuya.iot.suite.web.config.ProjectProperties
import com.tuya.iot.suite.web.model.request.login.LoginReq
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/04
 */
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
@Slf4j
class LoginControllerSpec extends Specification{

    @Autowired
    MockMvc mvc
    @Autowired
    UserService userService
    @Autowired
    ProjectProperties projectProperties
    void "测试登陆"(){
        given:
        def userAbility = userService.userAbility = Mock(UserAbility)
        userAbility.loginUser(_) >> {
            req->
                log.info("login==>{}",req)
                def token = new UserToken()
                token.access_token = "123456"
                token.refresh_token = "78910"
                token.uid = "u111"
                token.expire = 7200
                token
        }
        def data = LoginReq.builder().country_code(projectProperties.adminUserCountryCode)
                .user_name(projectProperties.adminUserName)
                .login_password(Sha256Util.encryption(projectProperties.adminUserPwd)).build()
        def json = JSON.toJSONString(data)
        when:
        expect: "登陆成功"
        mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType("application/json")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath('$.result.nick_name').value(projectProperties.adminUserName))
    }
}
