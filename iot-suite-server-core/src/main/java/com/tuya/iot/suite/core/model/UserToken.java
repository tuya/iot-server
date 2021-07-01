package com.tuya.iot.suite.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @author bade
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken implements Serializable {
    private String userId;
    @JsonProperty("nick_name")
    private String nickName;
    private String token;

    @JsonProperty("role_type")
    private List<String> roleType;

    @Override
    public String toString() {
        return "UserToken{"
                + "userId='" + userId + '\''
                + "nickName='" + nickName + '\''
                + ", token='" + token + '\''
                + ", roleType='" + roleType + '\''
                + '}';
    }
}
