package com.tuya.iot.server.service.notice.template;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Data
@AllArgsConstructor
public abstract class NoticeTemplate {

    String templateId;

    String templateParam;

}
