package com.tuya.iot.suite.ability.notification.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ToString
public class AppTemplateRequest extends BaseTemplateRequest {

    /**
     * 模板标题，长度为1~40 个字符
     */
    @ApiModelProperty(value = "模板标题", required = true)
    private String title;

}
