package com.tuya.iot.suite.service.enums;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
public enum RoleTypeEnum {
    /**
     * 系统管理员
     */
    admin,
    /**
     * 管理员
     */
    manager,
    /**
     * 普通用户
     */
    normal;

    public static final String SEPARATOR = "-";



    public static RoleTypeEnum fromRoleCode(String roleCode) {
        int pos = roleCode.indexOf(SEPARATOR);
        if (pos < 0) {
            return RoleTypeEnum.valueOf(roleCode);
        }
        String name = roleCode.substring(0, pos);
        return RoleTypeEnum.valueOf(name);
    }

    public boolean isAdmin() {
        return admin.equals(this);
    }

    public boolean isManager() {
        return manager.equals(this);
    }

    public boolean isNormal() {
        return normal.equals(this);
    }

    public static boolean isAdminRoleType(String roleType) {
        return admin.name().equals(roleType);
    }

    public static boolean isManagerRoleType(String roleType) {
        return manager.name().equals(roleType);
    }

    public static boolean isNormalRoleType(String roleType) {
        return normal.name().equals(roleType);
    }

    public static boolean isAdminRoleCode(String roleCode) {
        return admin.name().equals(roleCode);
    }

    public static boolean isManagerRoleCode(String roleCode) {
        return roleCode.startsWith(manager.name() + SEPARATOR);
    }

    public static boolean isNormalRoleCode(String roleCode) {
        return roleCode.startsWith(normal.name() + SEPARATOR);
    }

    public static boolean isValidRoleCode(String roleCode) {
        return isAdminRoleCode(roleCode) || isManagerRoleCode(roleCode) || isNormalRoleCode(roleCode);
    }
}
