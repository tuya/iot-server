package com.tuya.iot.suite.ability.idaas.model;

import com.tuya.iot.suite.ability.model.PageRequestInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolesPaginationQueryReq extends PageRequestInfo {
    /**
    角色code集合	否
     */
    List<String> roleCodes;
    /**String	角色code	否*/
    String roleCode;
    /**	String	角色名称	否*/
    String roleName;

}
