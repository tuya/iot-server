package com.tuya.iot.server.ability.notice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PushResult implements Serializable {

    boolean send_status;

}
