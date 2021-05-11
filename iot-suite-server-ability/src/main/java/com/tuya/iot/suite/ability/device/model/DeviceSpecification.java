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
public class DeviceSpecification implements Serializable {

    private static final long serialVersionUID = -4645014127674068502L;
    private String category;
    private List<DeviceFunctions> functions;
    private List<DeviceStatus> status;

    @Data
    public static class DeviceFunctions implements Serializable {
        private static final long serialVersionUID = -4612688818929414519L;
        private String code;
        private String desc;
        private String name;
        private String type;
        private String values;
    }

    @Data
    public static class DeviceStatus implements Serializable {

        private static final long serialVersionUID = -4980052570441016642L;
        private String code;
        private String name;
        private String type;
        private String values;
    }

}
