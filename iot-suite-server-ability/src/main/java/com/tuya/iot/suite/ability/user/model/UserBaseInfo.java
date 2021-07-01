package com.tuya.iot.suite.ability.user.model;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author mickey
 * @date 2021年06月03日 15:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseInfo implements Serializable {

    private String userId;

    private String userName;

    private String nickName;

    private List<IdaasRole> roles;

    private String createTime;


}
