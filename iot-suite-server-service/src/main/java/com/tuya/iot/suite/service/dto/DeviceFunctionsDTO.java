package com.tuya.iot.suite.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/20
 */
@Data
public class DeviceFunctionsDTO implements Serializable {
    private static final long serialVersionUID = 6664535325704943332L;

    private String code;
    private String desc;
    private String name;
    private String type;
    private String values;
}
