package com.tuya.iot.suite.ability.notice.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @author: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
@ToString
public class AppPushReq extends BasePushReq {
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 涂鸦体系的业务类型
     */
    private String biz_type;

}
