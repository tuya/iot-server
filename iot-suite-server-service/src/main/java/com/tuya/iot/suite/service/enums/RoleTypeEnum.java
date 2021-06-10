package com.tuya.iot.suite.service.enums;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
public enum RoleTypeEnum {
    /**
     * 系统管理员
     */
    admin(null),
    /**
     * 管理员
     */
    manager(admin),
    /**
     * 普通用户
     */
    normal(manager);

    public static final String SEPARATOR = "-";

    /**
     * 上级角色
     */
    RoleTypeEnum parent;

    RoleTypeEnum(RoleTypeEnum parent) {
        this.parent = parent;
    }

    public RoleTypeEnum getParent() {
        return parent;
    }

    public static RoleTypeEnum fromRoleCode(String roleCode) {
        int pos = roleCode.indexOf(SEPARATOR);
        if (pos < 0) {
            return RoleTypeEnum.valueOf(roleCode);
        }
        String name = roleCode.substring(0, pos);
        return RoleTypeEnum.valueOf(name);
    }

    /**
     * 小于等于目标角色类型
     */
    public boolean ltEq(RoleTypeEnum target) {
        return lt(this, target) || eq(this, target);
    }

    /**
     * 是否小于等于目标角色类型
     */
    public boolean ltEq(String roleCode) {
        RoleTypeEnum target = RoleTypeEnum.fromRoleCode(roleCode);
        return lt(this, target) || eq(this, target);
    }

    /**
     * 是否大于等于所有目标角色
     */
    public boolean gtEqAll(List<String> roleCodes) {
        return roleCodes.stream().allMatch(it -> gtEq(it));
    }

    public boolean gt(String roleCode) {
        return gt(this, RoleTypeEnum.fromRoleCode(roleCode));
    }

    public boolean gt(RoleTypeEnum target) {
        return gt(this, target);
    }

    public boolean gtEq(String roleCode) {
        RoleTypeEnum target = RoleTypeEnum.fromRoleCode(roleCode);
        return gt(this, target) || eq(this, target);
    }

    public boolean gtEq(RoleTypeEnum target) {
        return gt(this, target) || eq(this, target);
    }

    public boolean lt(String roleCode) {
        return lt(this, RoleTypeEnum.fromRoleCode(roleCode));
    }

    public boolean lt(RoleTypeEnum target) {
        return lt(this, target);
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

    /**
     * 是否大于目标角色类型
     */
    public static boolean gt(RoleTypeEnum source, RoleTypeEnum target) {
        RoleTypeEnum type = target.parent;
        while (type != null) {
            if (type.equals(source)) {
                return true;
            }
            type = type.parent;
        }
        return false;
    }

    /**
     * 是否小于目标角色类型
     */
    public static boolean lt(RoleTypeEnum source, RoleTypeEnum target) {
        RoleTypeEnum type = source.parent;
        while (type != null) {
            if (type.equals(target)) {
                return true;
            }
            type = type.parent;
        }
        return false;
    }

    /**
     * 是否等于目标角色类型
     */
    public static boolean eq(RoleTypeEnum roleType1, RoleTypeEnum roleType2) {
        return roleType1.equals(roleType2);
    }

}
