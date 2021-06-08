package com.tuya.iot.suite.ability.idaas.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/07
 */
@Data
@Builder
public class IdaasSpace {
    String spaceGroup;
    String spaceId;
    String owner;
    String spaceCode;
}
