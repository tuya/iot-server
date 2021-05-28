package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.model.PageDataVO;
import com.tuya.iot.suite.web.model.DeviceAlarmVO;
import com.tuya.iot.suite.web.model.criteria.DeviceAlarmListCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Response<PageDataVO<DeviceAlarmVO>> queryDeviceAlarms(@RequestBody DeviceAlarmListCriteria criteria){
        return Todo.todo();
    }
}
