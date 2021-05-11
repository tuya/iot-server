# General Management Portal
## Introduction
General Management Portal is application that implemented and expanded  the capability from Cloud Development Platform. The application is related to the project code in Cloud Development Platform. Can login in the application with user account created in the project in Cloud Development Platform，The application includes the following features:：
Asset Management：Change Password、Reset Password.
Asset Management：Create Devince、Delete Device、Edit Device and so on.
Device Management：Create Devince、Remove Device、Edit Device、Control Device and so on.
## Run this project
1.  Import the app source code into IDE，and initialized the settings about the account applied from Cloud Development Platform in the ./iot-suit-starter/src/main/resources/application.properties file
    //The Access ID/Client ID  applied from Cloud Development Platform
    connector.ak=
    //The Access Secret/Client Secret applied from Cloud Development Platform
    connector.sk=
    // The Project Code  applied from Cloud Development Platform
    project.code=

2. Run this project as a Spring Boot app, run main method, or use Maven:
   $ ./mvn package
   $ java -jar iot-app-smart-office-starter/target/*.jar


## Documentation
For more Documentation,  API Reference Doc, please check:
Application PRD Doc[https://wiki.tuya-inc.com:7799/page/89527987](https://wiki.tuya-inc.com:7799/page/89527987 "https://wiki.tuya-inc.com:7799/page/89527987")
The API Reference Doc[https://wiki.tuya-inc.com:7799/page/85629879](https://wiki.tuya-inc.com:7799/page/85629879 "https://wiki.tuya-inc.com:7799/page/85629879")

### Support
You can get support from Tuya with the following methods:
Tuya Smart Help Center:[https://support.tuya.com/en/help](https://support.tuya.com/en/help "https://support.tuya.com/en/help")
Technical Support Council: [https://service.console.tuya.com ](https://service.console.tuya.com  "https://service.console.tuya.com ")

