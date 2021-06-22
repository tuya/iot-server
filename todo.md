# 下期优化点
- 异常时返回给前端新增字段traceId，将openid的traceId透传过来
- spaceCode自动生成策略。
  
权限空间唯一标识（二元组 spaceGroup+spaceCode）。

这个二元组是全局唯一的，并不是其对应域下唯一的。

但是二元组是开发者手填的。这样就非常容易产生冲突。

比如uid为1234的开发者申请了spaceGroup=tuya，spaceCode=abc的空间（假设申请的域类型为clientId，并且clientId=qwer）。
那么任何开发者无法再为任何域申请spaceGroup=tuya，spaceCode=abc的空间。

在日常环境有时候的确会遇到这样的问题。

现在制定策略：
如果权限空间作用域为开发者（iotuid），那么使用uid的哈希作为spaceCode。
如果权限空间作用域为项目（project），那么使用projectCode的哈希作为spaceCode。
如果权限空间作用域为应用（clientId），那么使用clientId（ak）的哈希作为spaceCode。

至于group，建议根据场景命名。
比如tuya-iot-dev，tuya-iot-test，tuya-iot-pre，tuya-iot-prod等

