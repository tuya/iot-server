package com.tuya.iot.suite.core.constant;

/**
 * <p> TODO
 *
 * @author 哲也
 * @since 2021/4/15
 */

public enum NoticeType {

    SMS(1, "SMS"),

    MAIL(2, "MAIL"),

    VOICE(3, "VOICE"),

    APP(4, "APP")
    ;

    public static NoticeType getType(int code) {
        NoticeType[] values = values();
        for (NoticeType type : values) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    private int code;

    private String desc;

    NoticeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
