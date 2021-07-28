
[中文版](README_zh.md) | [English](README.md)


# General Management Portal
## Overview

General Management Portal implements the industry capabilities of the cloud, flexibly integrates and extends the IoT applications.

This portal is linked with `projectCode` of projects on the [Cloud Development Platform](https://iot.tuya.com/cloud/). You need to log in with the user information corresponding to the `projectCode`. The following features are supported

- Account management: Modify and reset passwords.

- Asset management: Create, modify, and delete assets.

- Device management: Add, edit, control, and remove devices.

- Front-end project address: [iot-portal](https://github.com/tuya/iot-portal)

![Quick start](images/iot-server.gif)

## Start a project
### 1. Pull the project code and import it into the IDE.

    > git clone https://github.com/tuya/iot-suite-server.git

### 2. Configure parameters
#### Account of the Cloud Development Platform (required)
    Configure the account of the Cloud Development Platform application in the `application.properties` file under `iot-suite-server-web`.

    ```properties
    # The Access ID/Client ID/Project Code that you have applied for and obtained on the Cloud Development Platform
    connector.ak=
    connector.sk=
    project.code=
    ```
![Config.png](https://airtake-public-data-1254153901.cos.ap-shanghai.myqcloud.com/content-platform/hestia/1625642228a9c1cb190dd.png)

#### Template ID (optional)

To retrieve your password, you need to enable push notifications by <b>SMS</b> and <b>email</b>. You must apply for the template in advance.
* Apply for the email template: https://developer.tuya.com/en/docs/cloud/3f377cbcd3?id=Kagouv5mzqgdb
* Apply for the SMS template: https://developer.tuya.com/en/docs/cloud/7a37355b05?id=Kagp29so0orah

Enter the template ID in `application.properties` under `iot-suite-server-web`.

    ```properties
    # SMS template in Chinese
    captcha.notice.resetPassword.sms.templateId.cn=
    # SMS template in English
    captcha.notice.resetPassword.sms.templateId.en=
    # Email template in Chinese
    captcha.notice.resetPassword.mail.templateId.cn=
    # Email template in English
    captcha.notice.resetPassword.mail.templateId.en=
    ```

**Note**:
* The template application parameter format is `{"code": "%s","timeLimit": "%d"}`.
* The template is not required if you do not use the password retrieval function.

### 3. Build a project
Run the following command to build a executable .jar package. The output path under the module of `iot-suite-we` in current project:
`./iot-suite-server/iot-suite-server-web/target`.

    > mvn clean install -U -Dmaven.test.skip=true

### 4. Run the project

    > cd ./iot-suite-server/iot-suite-server-web/target
    > 
    > java -jar iot-suite-server-web/target/iot-suite-server-{version}.jar

#### Dependencies & Compatibility

| Framework   | Version | Spring-boot Dependencies | 
| -------------- | ------------- |------------- |
| iot-server           | 1.0.0 ~ 1.1.2         | 1.x.x |

### Technical support

You can get technical support from Tuya in the following services:

Help Center: https://support.tuya.com/en/help

Service & Support: https://service.console.tuya.com
