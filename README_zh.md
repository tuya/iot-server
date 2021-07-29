
<img src="doc/images/tuya_logo.png" width="28%" height="28%" />

# Iot Server

[中文版](README_zh.md) | [English](README.md)

## 介绍
`iot-server` 是基于涂鸦云的 Saas 公版统一管控台应用，能够让开发者快速实现、灵活集成、自由扩展涂鸦云端的行业能力。目前框架已内置集成涂鸦云端行业通用的功能特性，能够做到开箱即用，一键部署。

该统一管理平台与云开发平台项目的 projectCode 做关联，需要使用 projectCode 对应的用户信息登录，主要包括以下特性功能：

* 账号管理：修改密码、重置密码
* 资产管理：创建资产、修改资产、删除资产等
* 设备管理：增加设备、移除设备、编辑设备、控制设备
* 权限控制：新建角色，设置权限项、重制权限

##  如何使用 IOT Server
  我们为您准备了一篇[快速入门IOT Server](https://developer.tuya.com/cn/docs/iot/SaaSDevelopmentFramework_backend?id=Kaqcx9hwc9i62)的文档

  您也可以了解完整的 SaaS 开发框架体系[SaaS 开发框架](https://developer.tuya.com/cn/docs/iot/SaaSDevelopmentFramework?id=Kaps8jd0mowem)的文档


## 文档

前端项目地址请参阅: [iot-portal](https://github.com/tuya/iot-portal)

Iot Server 底层云端对接使用 [tuya-connector](https://github.com/tuya/tuya-connector/tree/f62deb6c4738d7e80868268b29379c647798ed9c) 实现，你可以参考文档获得更多的信息。

关于更多涂鸦云端 openapi 接口可以查看 [文档](https://developer.tuya.com/cn/docs/iot/api-reference?id=Ka7qb7vhber64) 。

所有最新和长期的通知也可以在这里找到 [Github notice issue](https://github.com/tuya/iot-server/issues) 。


## 文档结构

项目代码结构如下：

* **iot-server-core**: 公共层，提供通用工具和模型
* **iot-server-ability**: 能力层，定义云平台接口
* **iot-server-service**: 业务逻辑层，实现开发者自定义业务逻辑
* **iot-server-web**: web接口层，提供前端外部调用接口


  ![config](doc/images/code-structure.png)

Iot Server [快速入门指南](doc/images/quick_start.md)

## 版本列表

| 框架 | release 版本 | JDK 版本 | Spring-boot 依赖 | 
| -------------- | ------------- |------------- |------------- |
| iot-server| 1.0.0 ~ 1.1.2 | 1.8`↑` |  1.5.x.RELEASE `↑` |

## Bug 和 反馈
对于错误报告，问题和讨论请提交到 [GitHub Issue](https://github.com/tuya/iot-server/issues)

## 如何获得技术支持

可以通过以下链接获得帮助

* 涂鸦智能帮助中心: [https://support.tuya.com/en/help](https://support.tuya.com/en/help "https://support.tuya.com/en/help")

* 涂鸦智能全球化智能平台: [https://service.console.tuya.com ](https://service.console.tuya.com  "https://service.console.tuya.com ")

欢迎加入微信群参与讨论分享：

<img src="doc/images/discussion-group.png" width="40%" height="40%" />

## Licenses

更多信息，请参考 [LICENSE](LICENSE)  文件。