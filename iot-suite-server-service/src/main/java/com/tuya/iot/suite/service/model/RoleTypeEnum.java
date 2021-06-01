package com.tuya.iot.suite.service.model;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
public enum RoleTypeEnum {
    /**系统管理员*/
    sysadmin(null),
    /**管理员*/
    admin(sysadmin),
    /**普通用户*/
    normal(admin);

    /**上级角色*/
    RoleTypeEnum parent;
    RoleTypeEnum(RoleTypeEnum parent){
        this.parent = parent;
    }

    public RoleTypeEnum getParent() {
        return parent;
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
    public boolean isSysadmin(RoleTypeEnum roleTypeEnum){
        return sysadmin.equals(roleTypeEnum);
    }

}
