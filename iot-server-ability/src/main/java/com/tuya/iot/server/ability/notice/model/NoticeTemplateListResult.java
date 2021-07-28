package com.tuya.iot.server.ability.notice.model;

import lombok.Data;

import java.util.List;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/5/11
 */
@Data
public class NoticeTemplateListResult {

    private long total;

    private List<NoticeTemplateList> list;

    private boolean has_more;

    @Data
    static class NoticeTemplateList {
        private String template_id;
        private String name;
        private String content;
        private int status;
    }

}

