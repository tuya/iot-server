# 统一管理平台
## 介绍
统一管理平台是实现云端行业能力，能灵活集成、扩展IoT的统一管控台应用。该统一管理平台与云开发平台项目的projectCode做关联，需要使用projectCode对应的用户信息登录，主要包括以下内容：
账号管理：修改密码、重置密码
资产管理：创建资产、修改资产、删除资产等
设备管理：增加设备、移除设备、编辑设备、控制设备等
## 启动项目
1. 将项目代码导入到IDE，在./iot-suite-starter/src/main/resources/application.properties文件中配置云开发平台应用的账号
   //在云开发平台申请的Access ID/Client ID
   connector.ak=
   //在云开发平台申请的Access Secret/Client Secret
   connector.sk=
   //在云开发平台申请的Project Code
   project.code=

2. 以Spring Boot Starter 方式启动项目，执行main方法，或者用Maven
   $ ./mvn package
   $ java -jar iot-app-smart-office-starter/target/*.jar

### 如何获得技术支持
可以通过以下链接获得帮助
涂鸦智能帮助中心:[https://support.tuya.com/en/help](https://support.tuya.com/en/help "https://support.tuya.com/en/help")
涂鸦智能全球化智能平台:[https://service.console.tuya.com ](https://service.console.tuya.com  "https://service.console.tuya.com ")

