package com.tuya.iot.suite.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    String alarm_id;
    @ApiModelProperty("告警名称")
    String alarm_name;
    @ApiModelProperty("告警内容")
    String alarm_content;
    @ApiModelProperty("设备id")
    String device_id;
    @ApiModelProperty("告警时间")
    String alarm_time;
    @ApiModelProperty("告警等级")
    String alarm_level;
    @ApiModelProperty("告警状态")
    String alarm_status;
}
