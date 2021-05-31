# General Management Portal
## Overview

General Management Portal implements the industry capabilities of the cloud, flexibly integrates and extends the IoT applications. 

This portal is linked with `projectCode` of projects on the [Cloud Development Platform](https://iot.tuya.com/cloud/). You need to log in with the user information corresponding to the `projectCode`, including the following items:

- Account management: Modify and reset passwords.
- Asset management: Create, modify, and delete assets.
- Device management: Add, remove, edit, and control devices.

## Start project

1. Import the project code into the IDE, and configure the account of the Cloud Development Platform application in the `./iot-suite-server-web/src/main/resources/application.properties` file.
   
    // The Access ID/Client ID that you have applied for and obtained on the Cloud Development Platform
    connector.ak=
    // The Access Secret/Client Secret that you have applied for and obtained on the Cloud Development Platform
    connector.sk=
    // The Project Code that you have applied for and obtained on the Cloud Development Platform
    project.code=

2. Start the project in the mode of Spring Boot Starter, execute the main method, or use Maven.
   
    $ ./mvn package
    $ java -jar iot-suite-server-web/target/*.jar

### Technical support

You can get technical support from Tuya in the following services:

Help Center: https://support.tuya.com/en/help

Service & Support: https://service.console.tuya.com
