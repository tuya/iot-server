package com.tuya.iot.suite.util

import com.alibaba.fastjson.JSON
import com.tuya.iot.suite.service.util.PermTemplateUtil
import groovy.util.logging.Slf4j
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/03
 */
@Slf4j
class PermTemplateUtilSpec extends Specification{
    void "测试加载权限模版为列表"(){
        given:
        when:
        def trees = PermTemplateUtil.loadAsList("classpath:template/permissions-admin.json")
        then:
        trees.collect{it.code}
                .containsAll(['1000','1001','2001','2000'])
        //log.info(JSON.toJSONString(trees,true))
    }
}
