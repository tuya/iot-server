package com.tuya.iot.suite.ability.user.model;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

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

    private List<IdaasRole> roles;

    private String createTime;


}
