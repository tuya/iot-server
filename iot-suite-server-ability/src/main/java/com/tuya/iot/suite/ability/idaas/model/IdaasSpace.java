package com.tuya.iot.suite.ability.idaas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdaasSpace {
    String spaceGroup;
    String spaceId;
    String owner;
    String spaceCode;
}
