package com.tuya.iot.server.ability.idaas.ability;

import com.tuya.iot.server.ability.idaas.model.*;

/**
 * idaas的用户能力，区分iot平台的用户能力
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface IdaasUserAbility {

    Boolean createUser(String spaceId, IdaasUserCreateReq request);

    Boolean updateUser(String spaceId, String uid, IdaasUserUpdateReq req);

    Boolean deleteUser(String spaceId, String uid);

    IdaasUser getUserByUid(String spaceId, String uid);

    IdaasPageResult<IdaasUser> queryUserPage(String spaceId, IdaasUserPageReq req);
}
