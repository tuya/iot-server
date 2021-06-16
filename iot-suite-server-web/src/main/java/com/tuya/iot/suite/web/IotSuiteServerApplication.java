package com.tuya.iot.suite.web;

import com.tuya.connector.spring.annotations.ConnectorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 本地调试启动指南：
 * 方式一
 * 为了将本地调试的一些properties配置提交到github，需要将本地调试的properties文件外置。
 * 假设本地的properties全路径为/Users/tuya/properties/application-local.properties
 * 例如通过mvn插件启动
 * $ mvn spring-boot:run -Dspring.profiles.active=local \
 * -Dspring.config.location=/Users/tuya/properties/application-local.properties
 *
 * 例如在idea中启动
 * 在Run->Edit Configurations->Edit Configuration templates->
 * Application->Cli Arguments to your application
 * 添加
 * --spring.profiles.active=local \
 * --spring.config.location=/Users/tuya/properties/application-local.properties
 * 然后运行
 *
 * 单元测试
 * 在Run->Edit Configurations->Edit Configuration templates->
 * Junit->VM Options
 * 添加
 * -Dspring.profiles.active=local \
 * -Dspring.config.location=/Users/tuya/properties/application-local.properties
 *
 * 注意：指定命令行参数和指定jvm参数的区别。
 *
 * 方式二
 * 在resources目录添加application-local.properties文件
 * （.ignore中已经设置了过滤）
 * 然后修改application.properties，
 * 将spring.profiles.active=prod改为spring.profiles.active=local
 * 启动项目
 *
 *
 * 忠告！！！
 * 忠告！！！
 * 忠告！！！
 * 如果有任务需要在启动的时候做，一定要catch住异常！！！
 * 否则会导致应用启动失败。这样会被当成部署失败！！次数多了会通知到领导。
 * 但是实际上并不是真的部署的问题，而是其他任务（比如openapi调用超时）影响了部署，导致误判。
 * 所以为了不被误判为部署失败，一定要catch住异常，然后在部署成功之后
 * 通过重启或其他操作重试任务。
 */

@SpringBootApplication(scanBasePackages = "com.tuya.iot.suite")
@ConnectorScan(basePackages = {"com.tuya.iot.suite.ability.*.connector"})
public class IotSuiteServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotSuiteServerApplication.class, args);
    }

}
