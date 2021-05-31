package com.tuya.iot.suite.ability.idaas.ability;

import com.tuya.iot.suite.ability.idaas.model.IdaasUser;
import com.tuya.iot.suite.ability.idaas.model.IdaasUserCreateReq;
import com.tuya.iot.suite.ability.idaas.model.IdaasUserUpdateReq;

/**
 * idaas的用户能力，区分iot平台的用户能力
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface IdaasUserAbility {

    Boolean createUser(Long spaceId,IdaasUserCreateReq request);

    Boolean updateUser(Long spaceId, String uid, IdaasUserUpdateReq req);

    Boolean deleteUser(Long spaceId, String uid);

    IdaasUser getUserByUid(Long spaceId, String uid);
}
