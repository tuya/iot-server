package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.model.PageDataVO;
import com.tuya.iot.suite.web.model.DeviceAlarmVO;
import com.tuya.iot.suite.web.model.criteria.DeviceAlarmListCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 设备预警
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/27
 */
@RestController
@RequestMapping("/device-alarms")
@Api(description = "设备告警")
public class DeviceAlarmController {

    @ApiOperation("查询设备告警")
    @GetMapping
    public Response<PageDataVO<DeviceAlarmVO>> queryDeviceAlarms(
            @ApiParam(value = "告警等级。通知,轻微,严重")
            @RequestParam String alarmLevel,
            @ApiParam(value = "告警时间类型。今天”、“昨天”、“近3天”、“近7天”、“近30天”和“近半年",required = true)
            @RequestParam String alarmDateType,
            @ApiParam(value = "告警状态。未处理、已处理")
            @RequestParam String alarmStatus,
            @ApiParam(value = "搜索词。告警名称/设备id")
            @RequestParam String searchKey
    ){
        return Todo.todo();
    }
}
