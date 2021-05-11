package com.tuya.iot.suite.ability.device.ability;

import com.tuya.iot.suite.ability.device.model.*;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/27
 */
public interface DeviceAbility {

    Boolean deleteDevice(String deviceId);

    Boolean deleteDevices(String deviceIds);

    Boolean modifyDevice(String deviceId, DeviceModifyRequest request);

    Devices.Device selectDevice(String deviceId);

    Devices selectDevices(String deviceIds);

    List<DeviceStatuses.DeviceStatus> selectDeviceStatus(String deviceId);

    List<DeviceStatuses> selectDeviceStatuses(String deviceIds);

    DeviceSpecification selectDeviceFunctions(String deviceId);

    DeviceSpecification selectDeviceSpecification(String deviceId);

    Boolean commandDevice(String deviceId, DeviceCommandRequest request);
}
