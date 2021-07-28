package com.tuya.iot.server.web.controller;

import com.tuya.iot.server.web.model.response.alarm.DeviceAlarmVO;
import com.tuya.iot.server.core.constant.Response;
import com.tuya.iot.server.core.util.Todo;
import com.tuya.iot.server.service.alarm.AlarmService;
import com.tuya.iot.server.core.model.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
public class DeviceAlarmController {

    @Autowired
    AlarmService alarmService;

    @ApiOperation("查询设备告警")
    @GetMapping
    @RequiresPermissions("5001")
    public Response<PageVO<DeviceAlarmVO>> queryDeviceAlarms(
            @ApiParam(value = "告警等级。通知,轻微,严重")
            String alarmLevel,
            @ApiParam(value = "告警时间类型。今天”、“昨天”、“近3天”、“近7天”、“近30天”和“近半年",required = true)
            @RequestParam String alarmDateType,
            @ApiParam(value = "告警状态。未处理、已处理")
            String alarmStatus,
            @ApiParam(value = "搜索词。告警名称/设备id")
            String searchKey
    ){
        return Todo.todo();
    }
}
