package com.tuya.iot.suite.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
