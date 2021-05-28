package com.tuya.iot.suite.web.model.criteria;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListCriteria {
    String search_key;
    String role_code;
}
