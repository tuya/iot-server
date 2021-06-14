package com.tuya.iot.suite.web.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "project")
@Component
public class ProjectProperties {
    String permissionSpaceId;
    String permissionGroup;
    String permissionSpaceCode;
    String permissionSpaceOwner;
    Boolean permissionAutoInit;
    String code;
    String name;
    String adminUserName;
    String adminUserId;
    String adminUserPwd;
    String adminUserCountryCode;
}


