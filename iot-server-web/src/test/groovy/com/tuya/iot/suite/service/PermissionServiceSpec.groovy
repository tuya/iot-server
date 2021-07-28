package com.tuya.iot.suite.service

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.tuya.iot.server.ability.idaas.ability.PermissionAbility
import com.tuya.iot.server.ability.idaas.model.IdaasPermission
import com.tuya.iot.server.ability.idaas.model.PermissionTypeEnum
import com.tuya.iot.server.service.idaas.PermissionService
import com.tuya.iot.server.service.util.PermTemplateUtil
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.ResourceUtils
import org.springframework.util.StreamUtils

import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
@Slf4j
class PermissionServiceSpec extends BaseSpec{
    @Autowired
    PermissionService permissionService
    void "测试查询我的权限树"(){

    }

}
