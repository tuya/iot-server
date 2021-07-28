package com.tuya.iot.server.service.device.impl;

import com.google.common.collect.Lists;
import com.tuya.iot.server.ability.device.ability.DeviceAbility;
import com.tuya.iot.server.ability.device.model.*;
import com.tuya.iot.server.core.util.SimpleConvertUtil;
import com.tuya.iot.server.service.device.DeviceService;
import com.tuya.iot.server.service.dto.DeviceDTO;
import com.tuya.iot.server.service.dto.DeviceFunctionsDTO;
import com.tuya.iot.server.service.dto.DeviceStatusDTO;
import lombok.SneakyThrows;
import org.apache.pulsar.shade.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/27
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceAbility deviceAbility;

    @Override
    public DeviceDTO getDeviceBy(String deviceId) {
        Devices.Device device = deviceAbility.selectDevice(deviceId);
        DeviceDTO deviceDTO = SimpleConvertUtil.convert(device, DeviceDTO.class);
        DeviceSpecification deviceSpec = deviceAbility.selectDeviceSpecification(deviceId);
        List<DeviceStatuses.DeviceStatus> deviceStatuses = deviceAbility.selectDeviceStatus(deviceId);
        deviceDTO.setFunctions(SimpleConvertUtil.convert(deviceSpec.getFunctions(), DeviceFunctionsDTO.class));
        deviceDTO.setStatus(SimpleConvertUtil.convert(deviceStatuses, DeviceStatusDTO.class));
        return deviceDTO;
    }

    @Override
    @SneakyThrows
    public List<DeviceDTO> getDevicesBy(List<String> deviceIdList) {
        if (CollectionUtils.isEmpty(deviceIdList)) {
            return Lists.newArrayList();
        }
        String deviceIds = StringUtils.join(deviceIdList,",");
        List<DeviceDTO> result = new ArrayList<>();
        Devices devices = deviceAbility.selectDevices(deviceIds);
        List<DeviceStatuses> deviceStatusesList = deviceAbility.selectDeviceStatuses(deviceIds);
        for (Devices.Device device : devices.getList()) {
            DeviceStatuses deviceStatuses = deviceStatusesList.stream().filter(item -> device.getId().equals(item.getId())).findFirst().orElse(null);
            if (Objects.nonNull(deviceStatuses)) {
                List<DeviceStatuses.DeviceStatus> status = deviceStatuses.getStatus();
                DeviceDTO deviceDTO = SimpleConvertUtil.convert(device, DeviceDTO.class);
                List<DeviceStatusDTO> deviceStatusDTOList = SimpleConvertUtil.convert(status, DeviceStatusDTO.class);
                deviceDTO.setStatus(deviceStatusDTOList);
                result.add(deviceDTO);
            }
        }
        return result;
    }

    @Override
    public DeviceSpecification selectDeviceSpecification(String deviceId) {
        return deviceAbility.selectDeviceSpecification(deviceId);
    }

    @Override
    public Boolean deleteDeviceBy(String deviceId) {
        return deviceAbility.deleteDevice(deviceId);
    }

    @Override
    public Boolean modifyDevice(String deviceId, DeviceModifyRequest request) {
        return deviceAbility.modifyDevice(deviceId, request);
    }

    @Override
    public Boolean commandDevice(String deviceId, DeviceCommandRequest request) {
        return deviceAbility.commandDevice(deviceId, request);
    }
}
