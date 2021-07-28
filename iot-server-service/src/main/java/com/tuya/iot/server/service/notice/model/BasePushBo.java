package com.tuya.iot.server.service.notice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/12
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasePushBo {

    String templateId;

    String templateParam;
}
