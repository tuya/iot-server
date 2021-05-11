package com.tuya.iot.suite.ability.notification.model;

import lombok.Data;
import lombok.ToString;
import org.apache.pulsar.shade.io.swagger.annotations.ApiModel;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ToString
@ApiModel(description = "语音发送通知模板申请")
public class VoiceTemplateRequest extends BaseTemplateRequest {

}
