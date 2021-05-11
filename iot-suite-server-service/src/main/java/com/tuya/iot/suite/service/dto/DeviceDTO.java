package com.tuya.iot.suite.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/9
 */
@Data
public class DeviceDTO implements Serializable {

    private static final long serialVersionUID = -2467502960417705875L;

    /**
     * 设备编号
     */
    private String id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 用户Id
     */
    private String uid;

    /**
     * 密钥
     */
    private String local_key;

    /**
     * 产品类别
     */
    private String category;

    /**
     * 产品Id
     */
    private String product_id;

    /**
     * 产品名称
     */
    private String product_name;

    /**
     * 判断是否为子设备
     */
    private Boolean sub;

    /**
     * 设备唯一标识
     */
    private String uuid;

    /**
     * 设备在线状态
     */
    private Boolean online;

    /**
     * 设备激活时间，时间戳，精确到秒
     */
    private Long active_time;

    /**
     * 设备图标
     */
    private String icon;

    /**
     * 设备IP
     */
    private String ip;

    /**
     * 设备创建时间，时间戳，精确到秒
     */
    private Long create_time;

    /**
     * 设备更新时间，时间戳，精确到秒
     */
    private Long update_time;

    /**
     * 时区，比如: +08:00
     */
    private String time_zone;

    /**
     * 设备状态集合
     */
    private List<DeviceStatusDTO> status;

    /**
     * 设备功能集合
     */
    private List<DeviceFunctionsDTO> functions;
}
