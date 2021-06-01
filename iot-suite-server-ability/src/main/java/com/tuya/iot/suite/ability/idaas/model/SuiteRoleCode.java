package com.tuya.iot.suite.ability.idaas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @description 由于没有云端没有 角色类型，所以我们通过角色编码来实现。
 * 云端角色编码 = 管控台角色类型:管控台角色编码
 * 例如 admin:adminX
 * @author benguan.zhou@tuya.com
 * @date 2021/06/01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuiteRoleCode {
    static SuiteRoleCode EMPTY = new SuiteRoleCode();
    String type;
    String code;

    /**
     * 云端 角色编码 转 管控台角色类型和编码
     * @param cloudRoleCode
     * @return
     */
    public static SuiteRoleCode fromCloudRoleCode(String cloudRoleCode){
        if(!StringUtils.hasText(cloudRoleCode)){
            return EMPTY;
        }
        int pos = cloudRoleCode.indexOf(':');
        if(pos < 1){
            throw new RuntimeException("invalid role code :" + cloudRoleCode);
        }
        return builder().type(cloudRoleCode.substring(0,pos)).code(cloudRoleCode.substring(pos+1)).build();
    }
    public String toCloudRoleCode(){
        if(type==null){
            return null;
        }
        return type+':'+code;
    }
    public static String randomCode(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
