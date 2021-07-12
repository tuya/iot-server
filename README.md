[English](README.md) | [中文版](README_zh.md)

# General Management Portal
## Overview

General Management Portal is a general management applications that implements the industry capabilities of the cloud, flexibly integrates and extends the IoT applications.

This portal is linked with `projectCode` of projects on the [Cloud Development Platform](https://iot.tuya.com/cloud/). You need to log in with the user information corresponding to the `projectCode`, including the following items:

- Account management: Modify and reset passwords.
  
- Asset management: Create, modify, and delete assets.
  
- Device management: Add, remove, edit, and control devices.

Front-end project address: [iot-portal](https://github.com/tuya/iot-portal)

![quick start](iot-suite-server.gif)


## Start Project
### 1. pull the project from github and import it into the ide.
> git clone https://github.com/tuya/iot-suite-server.git

### 2. Parameter Setting
#### Project cCnfiguration（required）
Developer have to configure the account of the Cloud Development Platform in `application.properties` file under the module of `iot-suite-server-web`.
   ```properties
   # Access ID/Client ID/Project Code
   connector.ak=
   connector.sk=
   project.code=
   ```
![config](img.png)

#### Template ID（Not required）

The function of 'reset password' would be relied on the notification of sms & mial. Developer have to apply fot the templates before use the function of 'reset password'.

* Template for mail：[https://developer.tuya.com/cn/docs/cloud/3f377cbcd3?id=Kagouv5mzqgdb](https://developer.tuya.com/cn/docs/cloud/3f377cbcd3?id=Kagouv5mzqgdb)
* Template for sms：[https://developer.tuya.com/cn/docs/cloud/7a37355b05?id=Kagp29so0orah](https://developer.tuya.com/cn/docs/cloud/7a37355b05?id=Kagp29so0orah)

在 iot-suite-server-web 下的 `application.properties` 填入申请后的模板ID
   ```properties
#短信中文模板
captcha.notice.resetPassword.sms.templateId.cn=
#短信英文模板
captcha.notice.resetPassword.sms.templateId.en=
#邮件中文模板
captcha.notice.resetPassword.mail.templateId.cn=
#邮件中文模板
captcha.notice.resetPassword.mail.templateId.en=
   ```
注：
* 模板申请参数格式为 `{"code": "%s","timeLimit": "%d"}`
* 如果不使用找回密码功能，无需申请模板

### 3. 构建项目
执行如下命令构建可运行 jar 包，输出路径在 `iot-suite-server-web/target`
> mvn clean install -U -Dmaven.test.skip=true

### 4. 运行项目

> java -jar iot-suite-server-web/target/iot-suite-server-{version}.jar

### Technical support

You can get technical support from Tuya in the following services:

Help Center: https://support.tuya.com/en/help

Service & Support: https://service.console.tuya.com
