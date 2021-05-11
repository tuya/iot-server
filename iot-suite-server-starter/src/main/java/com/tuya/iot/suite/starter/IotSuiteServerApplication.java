package com.tuya.iot.suite.starter;

import com.tuya.connector.spring.annotations.ConnectorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tuya.iot.suite")
@ConnectorScan(basePackages = {"com.tuya.iot.suite.ability.*.connector"})
public class IotSuiteServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSuiteServerApplication.class, args);
    }

}
