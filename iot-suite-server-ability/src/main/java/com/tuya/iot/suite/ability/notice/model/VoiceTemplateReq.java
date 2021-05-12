package com.tuya.iot.suite.ability.notice.model;

import lombok.Data;
import lombok.ToString;
import org.apache.pulsar.shade.io.swagger.annotations.ApiModel;

/**
 * @Description:
 * @author: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ToString
@ApiModel(description = "语音发送通知模板申请")
public class VoiceTemplateReq extends BaseTemplateReq {

}
