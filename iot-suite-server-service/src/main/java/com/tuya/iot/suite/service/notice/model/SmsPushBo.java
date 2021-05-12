package com.tuya.iot.suite.service.notice.model;

import lombok.Data;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/12
 */
@Data
public class SmsPushBo extends BasePushBo {

    /**
     * 国家码
     */
    private String countryCode;
    /**
     * 接收短信的手机号码
     */
    private String phone;
}
