package com.tuya.iot.suite.core.constant;

import lombok.Getter;

/**
 * @author mickey
 * @date 2021年06月02日 16:53
 */
public enum RoleType {
    /**
     * 系统管理员
     */
    ADMIN("admin","系统管理员"),
    /**
     *管理员
     */
    MANAGER("manager","管理员"),
    /**
     *
     */
    NORMAL("normal","普通角色"),
    ;

    RoleType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Getter
    private String code;

    @Getter
    private String desc;


    public static RoleType valueByCode(String roleCode) {
        for (RoleType roleType : values()) {
            if (roleCode.startsWith(roleType.code)) {
                return roleType;
            }
        }
        return null;
    }
}
