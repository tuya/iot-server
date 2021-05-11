package com.tuya.iot.suite.web.controller;


import com.tuya.iot.suite.ability.device.model.DeviceCommandRequest;
import com.tuya.iot.suite.ability.device.model.DeviceModifyRequest;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.SimpleConvertUtil;
import com.tuya.iot.suite.service.device.DeviceService;
import com.tuya.iot.suite.web.model.criteria.DeviceCommandCriteria;
import com.tuya.iot.suite.web.model.criteria.DeviceCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/27
 */
@RequestMapping("/device")
@RestController
@Api(description = "设备管理")
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @Value("${project.name}")
    private String projectName;

    @Value("${project.code}")
    private String projectCode;

    @ApiOperation(value = "根据设备ID查询设备信息")
    @RequestMapping(value = "/{device_id}", method = RequestMethod.GET)
    Response getDeviceBy(@PathVariable("device_id") String deviceId) {
        return new Response(deviceService.getDeviceBy(deviceId));
    }

    @ApiOperation(value = "根据设备ID删除设备")
    @RequestMapping(value = "/{device_id}", method = RequestMethod.DELETE)
    Response deleteDeviceBy(@PathVariable("device_id") String deviceId) {
        return new Response(deviceService.deleteDeviceBy(deviceId));
    }

    @ApiOperation(value = "根据设备ID修改设备")
    @RequestMapping(value = "/{device_id}", method = RequestMethod.PUT)
    Response modifyDeviceBy(@PathVariable("device_id") String deviceId, @RequestBody DeviceCriteria criteria) {
        return new Response(deviceService.modifyDevice(deviceId, SimpleConvertUtil.convert(criteria, DeviceModifyRequest.class)));
    }

    @ApiOperation(value = "根据设备ID查询设备指令集")
    @RequestMapping(value = "/specification/{device_id}", method = RequestMethod.GET)
    Response selectDeviceSpecification(@PathVariable("device_id") String deviceId) {
        return new Response(deviceService.selectDeviceSpecification(deviceId));
    }

    @ApiOperation(value = "控制设备")
    @RequestMapping(value = "/command/{device_id}", method = RequestMethod.POST)
    Response commandDevice(@PathVariable("device_id") String deviceId, @RequestBody List<DeviceCommandCriteria> criteriaList) {
        List<DeviceCommandRequest.Command> convert = SimpleConvertUtil.convert(criteriaList, DeviceCommandRequest.Command.class);
        DeviceCommandRequest request = new DeviceCommandRequest();
        request.setCommands(convert);
        return new Response(deviceService.commandDevice(deviceId, request));
    }

    @ApiOperation(value = "设备配网二维码信息")
    @GetMapping(value = "/qrcode")
    public Response<Map<String, String>> qrcode() {
        Map<String, String> map = new HashMap<>(2);
        map.put("project_name", projectName);
        map.put("project_code", projectCode);
        return Response.buildSuccess(map);
    }

}
