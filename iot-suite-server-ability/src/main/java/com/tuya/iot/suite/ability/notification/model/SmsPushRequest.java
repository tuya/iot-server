package com.tuya.iot.suite.ability.notification.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ToString
public class SmsPushRequest extends BasePushRequest {

    /**
     * 国家码
     */
    private String country_code;
    /**
     * 接收短信的手机号码
     */
    private String phone;

}
