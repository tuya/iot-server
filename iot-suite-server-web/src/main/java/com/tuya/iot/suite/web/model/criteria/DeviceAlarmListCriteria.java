package com.tuya.iot.suite.web.model.criteria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author benguan.zhou
 * @date 2021/05/27
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAlarmListCriteria implements Serializable {

    private static final long serialVersionUID = 1;

    @ApiModelProperty(value = "告警等级。通知,轻微,严重")
    String alarm_level;

    @ApiModelProperty(value = "告警时间类型。今天”、“昨天”、“近3天”、“近7天”、“近30天”和“近半年")
    String alarm_date_type;

    @ApiModelProperty(value = "告警状态。未处理、已处理")
    String alarm_status;

    @ApiModelProperty(value = "搜索词。告警名称/设备id")
    String search_key;
}
