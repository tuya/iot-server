package com.tuya.iot.suite.service.model;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
public enum RoleTypeEnum {
    /**系统管理员*/
    admin(null),
    /**管理员*/
    manage(admin),
    /**普通用户*/
    normal(manage);

    private static final String SEPARATOR = "-";

    /**上级角色*/
    RoleTypeEnum parent;
    RoleTypeEnum(RoleTypeEnum parent){
        this.parent = parent;
    }

    public RoleTypeEnum getParent() {
        return parent;
    }

    public static RoleTypeEnum fromRoleCode(String roleCode){
        if(isAdmin(roleCode)){
            return admin;
        }
        int pos = roleCode.indexOf(SEPARATOR);
        if(pos<0){
            return null;
        }
        String name = roleCode.substring(0,pos);
        if(admin.name().equals(name)){
            return null;
        }
        return RoleTypeEnum.valueOf(name);
    }
    /**
     * 是否为target的子级角色 或 当前角色
     * */
    public boolean isOffspringOrSelf(RoleTypeEnum target){
        RoleTypeEnum type = this;
        while(type!=null){
            if(type.equals(target)){
                return true;
            }
            type = type.parent;
        }
        return false;
    }
    public static boolean isAdmin(RoleTypeEnum roleTypeEnum){
        return admin.equals(roleTypeEnum);
    }
    public static boolean isManage(RoleTypeEnum roleTypeEnum){
        return manage.equals(roleTypeEnum);
    }
    public static boolean isNormal(RoleTypeEnum roleTypeEnum){
        return normal.equals(roleTypeEnum);
    }
    public static boolean isAdmin(String roleCode){
        return admin.name().equals(roleCode);
    }
    public static boolean isManage(String roleCode){
        return roleCode.startsWith(manage.name()+SEPARATOR);
    }
    public static boolean isNormal(String roleCode){
        return roleCode.startsWith(normal.name()+SEPARATOR);
    }

    public static boolean isValidRoleCode(String roleCode){
        return isAdmin(roleCode) || isManage(roleCode) || isNormal(roleCode);
    }
}
