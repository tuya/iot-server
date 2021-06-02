package com.tuya.iot.suite.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Data
@ConfigurationProperties(prefix = "project")
@Component
public class ProjectProperties {
    Long permissionSpaceId;
    String permissionGroup;
    String permissionSpaceCode;
    String code;
    String name;
}


