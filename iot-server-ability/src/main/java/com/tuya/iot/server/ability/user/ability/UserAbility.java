package com.tuya.iot.server.ability.user.ability;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.open.api.model.PageResult;
import com.tuya.iot.server.ability.user.model.*;
import com.tuya.iot.server.ability.notice.model.ResetPasswordReq;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/26
 */
public interface UserAbility {

    UserToken loginUser(UserRegisteredRequest request);

    /**
     * Registered users
     *
     * @param request
     * @return
     */
    User registeredUser(UserRegisteredRequest request);

    /**
     * Destruction of the user
     *
     * @param userId
     * @return
     */
    Boolean destroyUser(String userId);


    /**
     * Modify Password
     *
     * @param userId
     * @param request
     * @return
     */
    Boolean modifyUserPassword(String userId, UserModifyRequest request);

    /**
     * Find users
     *
     * @param lastRowKey
     * @param pageSize
     * @return
     */
    PageResult<User> selectUsers(String lastRowKey, Integer pageSize);

    /**
     * Find user
     *
     * @param userId
     * @return
     */
    User selectUser(String userId);

    /**
     *  mobile country
     * @return
     */
    MobileCountries selectMobileCountries();

    /**
     * reset password
     *
     * @param resetPasswordReq
     * @return
     */
    Boolean resetPassword(@Body ResetPasswordReq resetPasswordReq);



}
