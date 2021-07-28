package com.tuya.iot.server.ability.notice.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: Medivh.chen@tuya.com
 * @date: 2021/04/14
 **/
@Data
public class BasePushResult implements Serializable {
    private static final long serialVersionUID = 3492172670400213632L;

    private Boolean send_status;
}
