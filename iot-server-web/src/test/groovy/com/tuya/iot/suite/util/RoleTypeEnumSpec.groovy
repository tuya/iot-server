package com.tuya.iot.suite.util

import com.tuya.iot.server.service.enums.RoleTypeEnum
import groovy.util.logging.Slf4j
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/10
 */
@Slf4j
class RoleTypeEnumSpec extends Specification{

    void testIsOffspringOrSelfOfAll(){
        given:
        RoleTypeEnum operatorRoleType = RoleTypeEnum.fromRoleCode('admin-0000')
        when:
        println operatorRoleType.gtEqAll(['manager-1000','normal-1000'])
        then:
        noExceptionThrown()
    }
}
