package com.tuya.iot.suite.web.model.response.alarm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/27
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAlarmVO {
    @ApiModelProperty("告警id")
    String alarmId;
    @ApiModelProperty("告警名称")
    String alarmName;
    @ApiModelProperty("告警内容")
    String alarmContent;
    @ApiModelProperty("设备id")
    String deviceId;
    @ApiModelProperty("告警时间")
    String alarmTime;
    @ApiModelProperty("告警等级")
    String alarmLevel;
    @ApiModelProperty("告警状态")
    String alarmStatus;
}
