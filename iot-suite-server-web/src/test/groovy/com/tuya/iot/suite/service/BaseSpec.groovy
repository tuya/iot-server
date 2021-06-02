package com.tuya.iot.suite.service

import com.tuya.iot.suite.web.IotSuiteServerApplication
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
@SpringBootTest(classes = IotSuiteServerApplication)
class BaseSpec extends Specification{
}
