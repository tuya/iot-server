package com.tuya.iot.suite.service

import com.tuya.iot.server.web.IotServerApplication
import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
@SpringBootTest(classes = IotServerApplication)
class BaseSpec extends Specification{
}
