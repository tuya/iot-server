package com.tuya.iot.suite.ability.device.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/27
 */
@Data
public class Devices implements Serializable {

    private static final long serialVersionUID = -729531291068553925L;
    private List<Device> list;
    private Boolean has_more;
    private Integer total;

    @Data
    public static class Device implements Serializable {

        private static final long serialVersionUID = -7824240582799706125L;
        private int active_time;
        private String asset_id;
        private String category;
        private int create_time;
        private String icon;
        private String id;
        private String ip;
        private String lat;
        private String local_key;
        private String lon;
        private String name;
        private boolean online;
        private String product_id;
        private String product_name;
        private boolean sub;
        private String time_zone;
        private int update_time;
        private String uuid;
    }
}
