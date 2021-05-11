package com.tuya.iot.suite.web.model.criteria;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/29
 */
@Data
public class DeviceCriteria implements Serializable {

    private static final long serialVersionUID = -4090218549895250029L;

    @ApiModelProperty(value = "设备名称")
    private String name;
}
