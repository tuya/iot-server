package com.tuya.iot.server.ability.device.connector;

import com.tuya.connector.api.annotations.*;
import com.tuya.iot.server.ability.device.model.*;
import com.tuya.iot.server.ability.device.ability.DeviceAbility;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/27
 */
public interface DeviceConnector extends DeviceAbility {

    @Override
    @DELETE("/v1.0/iot-03/devices/{device_id}")
    Boolean deleteDevice(@Path(("device_id")) String deviceId);

    @Override
    @DELETE("/v1.0/iot-03/devices")
    Boolean deleteDevices(@Query("device_ids") String deviceIds);

    @Override
    @PUT("/v1.0/iot-03/devices/{device_id}")
    Boolean modifyDevice(@Path("device_id") String deviceId, @Body DeviceModifyRequest request);

    @Override
    @GET("/v1.0/iot-03/devices/{device_id}")
    Devices.Device selectDevice(@Path("device_id") String deviceId);

    @Override
    @GET("/v1.0/iot-03/devices")
    Devices selectDevices(@Query("device_ids") String deviceIds);

    @Override
    @GET("/v1.0/iot-03/devices/{device_id}/status")
    List<DeviceStatuses.DeviceStatus> selectDeviceStatus(@Path("device_id") String deviceId);

    @Override
    @GET("/v1.0/iot-03/devices/status")
    List<DeviceStatuses> selectDeviceStatuses(@Query("device_ids") String deviceIds);

    @Override
    @GET("/v1.0/iot-03/devices/{device_id}/functions")
    DeviceSpecification selectDeviceFunctions(@Path("device_id") String deviceId);

    @Override
    @GET("/v1.0/iot-03/devices/{device_id}/specification")
    DeviceSpecification selectDeviceSpecification(@Path("device_id") String deviceId);

    @Override
    @POST("/v1.0/iot-03/devices/{device_id}/commands")
    Boolean commandDevice(@Path("device_id") String deviceId, @Body DeviceCommandRequest request);


}
