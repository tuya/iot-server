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
public class DeviceCommandCriteria implements Serializable {
    private static final long serialVersionUID = 7705926790813590443L;

    @ApiModelProperty(value = "指令")
    private String code;
    @ApiModelProperty(value = "指令值")
    private Object value;
}
