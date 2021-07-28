package com.tuya.iot.server.ability.notice.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ToString
public class SmsPushReq extends BasePushReq {

    /**
     * 国家码
     */
    private String country_code;
    /**
     * 接收短信的手机号码
     */
    private String phone;

}
