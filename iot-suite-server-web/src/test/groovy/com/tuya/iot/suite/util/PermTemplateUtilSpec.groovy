package com.tuya.iot.suite.util

import com.tuya.iot.suite.service.util.PermTemplateUtil
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/03
 */
class PermTemplateUtilSpec extends Specification{
    void "测试加载权限模版为列表"(){
        given:
        when:
        def trees = PermTemplateUtil.loadAsList("classpath:template/permissions-admin.json")
        then:
        trees.collect{it.permissionCode}
                .containsAll(['1000','1001','2001','2000'])
    }
}
