package com.tuya.iot.suite.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/10
 */
@Data
public class Area{
    String code;
    String parentCode;
    List<Area> children;
}
