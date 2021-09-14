package com.tuya.iot.server.service.util;

import com.tuya.iot.server.service.enums.RoleTypeEnum;

import java.util.UUID;

/**
 * @description 由于云端没有 角色类型，所以通过角色编码来实现。
 * @author benguan.zhou@tuya.com
 * @date 2021/06/01
 */
public class RoleCodeGenerator {

    /**
     * 云端 角色编码 转 管控台角色类型和编码
     * @return
     */
    public static String generate(String roleType){
        return roleType+ RoleTypeEnum.SEPARATOR+UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }
    public static String generate(RoleTypeEnum roleType,String suffix){
        return roleType.name()+RoleTypeEnum.SEPARATOR+suffix;
    }
    public static String generate(RoleTypeEnum roleType){
        return generate(roleType.name());
    }
}
