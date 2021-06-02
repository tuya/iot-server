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

    public static final String SEPARATOR = "-";

    /**上级角色*/
    RoleTypeEnum parent;
    RoleTypeEnum(RoleTypeEnum parent){
        this.parent = parent;
    }

    public RoleTypeEnum getParent() {
        return parent;
    }

    public static RoleTypeEnum fromRoleCode(String roleCode){
        if(isAdminRoleCode(roleCode)){
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
    public boolean isAdmin(){
        return admin.equals(this);
    }
    public boolean isManage(){
        return manage.equals(this);
    }
    public boolean isNormal(){
        return normal.equals(this);
    }

    public static boolean isAdminRoleType(String roleType){
        return admin.name().equals(roleType);
    }
    public static boolean isManageRoleType(String roleType){
        return manage.name().equals(roleType);
    }
    public static boolean isNormalRoleType(String roleType){
        return normal.name().equals(roleType);
    }

    public static boolean isAdminRoleCode(String roleCode){
        return admin.name().equals(roleCode);
    }
    public static boolean isManageRoleCode(String roleCode){
        return roleCode.startsWith(manage.name()+SEPARATOR);
    }
    public static boolean isNormalRoleCode(String roleCode){
        return roleCode.startsWith(normal.name()+SEPARATOR);
    }

    public static boolean isValidRoleCode(String roleCode){
        return isAdminRoleCode(roleCode) || isManageRoleCode(roleCode) || isNormalRoleCode(roleCode);
    }
}
