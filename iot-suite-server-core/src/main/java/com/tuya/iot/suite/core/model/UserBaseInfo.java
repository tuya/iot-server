package com.tuya.iot.suite.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月03日 15:12
 */
@Getter
@Setter
@ToString
@Builder
public class UserBaseInfo implements Serializable {

    private String userId;

    private String userName;

    private String nickName;

    private String roleCode;

    private String roleName;

    
}
