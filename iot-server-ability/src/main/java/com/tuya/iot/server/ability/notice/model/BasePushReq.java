package com.tuya.iot.server.ability.notice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description:
 * @author: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Setter
@Getter
public class BasePushReq implements Serializable {

    private static final long serialVersionUID = 3142886369056427304L;
    /**
     * 必须填写已审核通过的模板 ID
     */
    private String template_id;

    /**
     * 邮件模板变量对应的实际值，JSON 格式
     */
    private String template_param;
}
