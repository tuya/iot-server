package com.tuya.iot.suite.service.user;


import com.tuya.iot.suite.ability.idaas.model.IdaasUser;
import com.tuya.iot.suite.ability.idaas.model.IdaasUserCreateReq;
import com.tuya.iot.suite.ability.idaas.model.IdaasUserUpdateReq;
import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.ability.user.model.UserToken;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;

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
     * 获取手机号城市编码
     * @return
     */
    MobileCountries selectMobileCountries();

    /**
     * 发送密码重置验证码
     * @param bo
     * @return
     */
    boolean sendRestPasswordCaptcha(CaptchaPushBo bo);

    /**
     * 重置密码
     *
     * @param resetPasswordBo
     * @return
     */
    boolean resetPassword(ResetPasswordBo resetPasswordBo);

    Boolean createUser(Long spaceId, IdaasUserCreateReq request);

    Boolean updateUser(Long spaceId, String uid, IdaasUserUpdateReq req);

    Boolean deleteUser(Long spaceId, String uid);

    IdaasUser getUserByUid(Long spaceId, String uid);
}
