package com.tuya.iot.suite.ability.idaas.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @description 用户 批量角色 授权 请求
 * @author benguan.zhou@tuya.com
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserGrantRolesReq {

    String uid;

    List<String> roleCodes;

    /**
     * 隔离空间id
     */
    Long spaceId;

}
