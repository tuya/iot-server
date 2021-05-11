package com.tuya.iot.suite.ability.notification.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ApiModel("消息推送返回结果")
public class BasePushResult implements Serializable {
    private static final long serialVersionUID = 3492172670400213632L;
    /*
     *发送状态
     */
    @ApiModelProperty(value = "发送状态")
    private Boolean send_status;
}
