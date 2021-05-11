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
public class AppPushRequest extends BasePushRequest {
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 涂鸦体系的业务类型
     */
    private String biz_type;

}
