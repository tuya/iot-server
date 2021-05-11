package com.tuya.iot.suite.ability.notification.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@ApiModel("邮件模板申请返回结果")
public class TemplatesResult implements Serializable {


    private static final long serialVersionUID = 3342650224749194123L;

    /**
     * 模板 ID
     */
    @ApiModelProperty(value = "模板ID")
    private String template_id;
}
