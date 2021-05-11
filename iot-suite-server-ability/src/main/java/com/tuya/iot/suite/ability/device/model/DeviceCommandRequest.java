package com.tuya.iot.suite.ability.device.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/27
 */
@Data
public class DeviceCommandRequest implements Serializable {

    private static final long serialVersionUID = 905778645527307532L;
    private List<Command> commands;

    public DeviceCommandRequest() {
        this.commands = new ArrayList<>();
    }

    @Data
    public static class Command implements Serializable {
        private static final long serialVersionUID = -5973828433334266216L;
        private String code;
        private Object value;
    }
}
