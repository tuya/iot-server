package com.tuya.iot.suite.ability.notification.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Setter
@Getter
public class BaseTemplateRequest {

    /**
     * 模板名称，长度为1~30 个字符
     */
    @ApiModelProperty(value = "模板名称", required = true)
    private String name;

    /**
     * 模板内容，长度为1~100 个字符
     */
    @ApiModelProperty(value = "模板内容")
    private String content;
    /**
     * 通知栏推送类型，0：运营消息 1：系统消息
     */
    @ApiModelProperty(value = "通知栏推送类型", required = true)
    private Integer type;
    /**
     * 通知栏推送模板申请说明。
     */
    @ApiModelProperty(value = "通知栏推送模板申请说明", required = true)
    private String remark;
}
