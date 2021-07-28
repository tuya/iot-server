package com.tuya.iot.server.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/10
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Area{
    String code;
    String parentCode;
    List<Area> children;
}
