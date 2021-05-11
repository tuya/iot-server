package com.tuya.iot.suite.core.constant;

/**
 * <p> TODO
 *
 * @author 哲也（张梓濠 zheye.zhang@tuya.com）
 * @since 2021/4/15
 */

public enum NotificationType {

    SMS(1, new NotificationTemplate("", "")),

    MAIL(2, new NotificationTemplate("", "")),

    VOICE(3, new NotificationTemplate()),

    APP(4, new NotificationTemplate())
    ;

    public static NotificationType getType(int code) {
        NotificationType[] values = values();
        for (NotificationType type : values) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    private int code;

    private NotificationTemplate template;

    NotificationType(int code, NotificationTemplate template) {
        this.code = code;
        this.template = template;
    }

    public int getCode() {
        return code;
    }

    public NotificationTemplate getTemplate() {
        return template;
    }

}
