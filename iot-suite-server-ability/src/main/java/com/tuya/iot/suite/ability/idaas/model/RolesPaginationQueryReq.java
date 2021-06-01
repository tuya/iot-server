package com.tuya.iot.suite.ability.idaas.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/01
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolesPaginationQueryReq {
    /**
    角色code集合	否
     */
    List<String> roleCodes;
    /**String	角色code	否*/
    String roleCode;
    /**	String	角色名称	否*/
    String roleName;
    /**	Integer	每页容量	是*/
    Integer pageSize;
    /**	Integer	当前页码	是*/
    Integer pageNum;
}
