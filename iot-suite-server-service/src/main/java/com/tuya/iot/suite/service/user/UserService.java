package com.tuya.iot.suite.service.user;


import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.ability.user.model.UserToken;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;

/**
 * @Description 用户service$
 * @Author bade
 * @Since 2021/3/15 8:42 下午
 */
public interface UserService {

    /**
     * 修改用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Boolean modifyLoginPassword(String userId, String oldPassword, String newPassword);

    /**
     * 验证密码
     *
     * @param userName
     * @param password
     * @return
     */
    UserToken login(String userName, String password);

    /**
     * 发送验证码
     *
     * @param captchaPushBo
     * @return
     */
    boolean captchaPush(CaptchaPushBo captchaPushBo);

    /**
     * 获取手机号城市编码
     * @return
     */
    MobileCountries selectMobileCountries();
}
