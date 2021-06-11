package com.tuya.iot.suite.web

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
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
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.http.Cookie

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/04
 */
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
@Slf4j
class ControllerSpec extends Specification {

    @Autowired
    MockMvc mvc
    @Autowired
    ProjectProperties projectProperties
    String token

    void setup() {
        def data = LoginReq.builder().country_code(projectProperties.adminUserCountryCode)
                .user_name(projectProperties.adminUserName)
                .login_password(Sha256Util.encryption(projectProperties.adminUserPwd))
                .build()
        def json = JSON.toJSONString(data)
        def mvcResult = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType("application/json")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        def result = JSONObject.parseObject(mvcResult.response.contentAsString)
        token = result.get('result').get('token')
    }

    void "测试查询用户列表"() {
        given:
        when:
        def mvcResult = mvc.perform(MockMvcRequestBuilders.get("/users")
                .cookie(new Cookie('token', token))
                .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        println mvcResult.response.contentAsString
        then:
        noExceptionThrown()
    }

    void "测试查询角色列表"() {
        given:
        when:
        def mvcResult = mvc.perform(MockMvcRequestBuilders.get("/roles")
                .cookie(new Cookie('token', token))
                .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        println mvcResult.response.contentAsString
        then:
        noExceptionThrown()
    }

    void "测试角色权限列表"() {
        given:
        when:
        def mvcResult = mvc.perform(MockMvcRequestBuilders.get("/roles/permissions")
                .cookie(new Cookie('token', token))
                .contentType("application/json")
                .param('roleCode', 'normal-1000')
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        println mvcResult.response.contentAsString
        then:
        noExceptionThrown()
    }
}
