package com.tuya.iot.suite.service.model;

import com.tuya.iot.suite.core.exception.ServiceLogicException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @description 由于没有云端没有 角色类型，所以我们通过角色编码来实现。
 * @author benguan.zhou@tuya.com
 * @date 2021/06/01
 */
public class RoleCodeGenerator {

    /**
     * 云端 角色编码 转 管控台角色类型和编码
     * @return
     */
    public static String generate(String roleType){
        if(RoleTypeEnum.isAdminRoleType(roleType)){
            throw new RuntimeException("role type "+roleType+" not supported to generate role code!");
        }
        return roleType+RoleTypeEnum.SEPARATOR+UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }
}
