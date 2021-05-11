package com.tuya.iot.suite.service.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/15
 */
@Data
public class CaptchaPushBo implements Serializable {

    private static final long serialVersionUID = -5518500849834080370L;

    int type;

    String country_code;

    String phone;

    //String template_id;

    //String template_param;

    String email;

}
