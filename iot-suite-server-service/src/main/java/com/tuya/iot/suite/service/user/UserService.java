package com.tuya.iot.suite.service.user;


import com.tuya.iot.suite.ability.idaas.model.IdaasUser;
import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.core.model.PageVO;
import com.tuya.iot.suite.ability.user.model.UserBaseInfo;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;

import java.util.List;

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
    UserToken login(String spaceId,String userName, String password);

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

    Boolean createUser(String spaceId, UserRegisteredRequest req, List<String> roleCodes);

    Boolean updateUser(String spaceId,String operatUserId, String uid, String nickName,List<String> roleCodes);

    Boolean deleteUser(String spaceId, String uid);

    IdaasUser getUserByUid(String spaceId, String uid);

    /**
     *  修改用户密码
     * @param userName
     * @param newPwd
     * @return
     */
    Boolean updateUserPassword( String userName, String newPwd);

    /**
     * 批量删除ID
     * @param spaceId
     * @param userIds
     * @return
     */
    Boolean batchDeleteUser(String spaceId, String... userIds);

    PageVO<UserBaseInfo> queryUserByPage(String spaceId, String searchKey, String roleCode, Integer pageNo, Integer pageSize);
}
