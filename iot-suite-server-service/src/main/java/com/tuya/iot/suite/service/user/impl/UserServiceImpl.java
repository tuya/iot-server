package com.tuya.iot.suite.service.user.impl;

import com.tuya.connector.api.exceptions.ConnectorResultException;
import com.tuya.iot.suite.ability.idaas.ability.GrantAbility;
import com.tuya.iot.suite.ability.idaas.ability.IdaasUserAbility;
import com.tuya.iot.suite.ability.idaas.ability.RoleAbility;
import com.tuya.iot.suite.ability.idaas.model.*;
import com.tuya.iot.suite.ability.notice.model.ResetPasswordReq;
import com.tuya.iot.suite.ability.user.ability.UserAbility;
import com.tuya.iot.suite.ability.user.model.*;
import com.tuya.iot.suite.core.constant.CaptchaType;
import com.tuya.iot.suite.core.constant.ErrorCode;
import com.tuya.iot.suite.core.constant.NoticeType;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.model.PageVO;
import com.tuya.iot.suite.service.asset.AssetService;
import com.tuya.iot.suite.service.enums.RoleTypeEnum;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.notice.template.CaptchaNoticeTemplate;
import com.tuya.iot.suite.service.user.CaptchaService;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tuya.iot.suite.core.constant.ErrorCode.*;

/**
 * @Description 用户实现类
 * @Author bade
 * @Since 2021/3/15 8:46 下午
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAbility userAbility;
    @Autowired
    private IdaasUserAbility idaasUserAbility;
    @Autowired
    private RoleAbility roleAbility;
    @Autowired
    private GrantAbility grantAbility;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private AssetService assetService;

    /**
     * 修改用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public Boolean modifyLoginPassword(String userId, String oldPassword, String newPassword) {
        UserModifyRequest request = new UserModifyRequest();
        request.setOld_password(oldPassword);
        request.setNew_password(newPassword);
        Boolean success = userAbility.modifyUserPassword(userId, request);
        return success;
    }

    /**
     * 验证密码
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public com.tuya.iot.suite.core.model.UserToken login(String spaceId, String userName, String password) {
        UserToken loginToken = userAbility.loginUser(UserRegisteredRequest.builder()
                .username(userName)
                .password(password)
                .nick_name(userName).build());

        List<IdaasRole> roles = roleAbility.queryRolesByUser(spaceId, loginToken.getUid());
        com.tuya.iot.suite.core.model.UserToken userToken =
                new com.tuya.iot.suite.core.model.UserToken(loginToken.getUid(), userName, null, roles.stream().map(e -> e.getRoleCode()).collect(Collectors.toList()));
        return userToken;
    }

    @Override
    public MobileCountries selectMobileCountries() {
        return userAbility.selectMobileCountries();
    }

    @Override
    public boolean sendRestPasswordCaptcha(CaptchaPushBo bo) {
        NoticeType type;
        String code;
        CaptchaNoticeTemplate template;
        long timeout = 5;
        String unionId;
        if (!StringUtils.isEmpty(bo.getPhone()) && !StringUtils.isEmpty(bo.getCountryCode())) {
            unionId = bo.getCountryCode() + bo.getPhone();
            type = NoticeType.SMS;
            code = captchaService.generateCaptchaInPermitTime(CaptchaType.PASSWORD_REST, unionId, timeout * 60, 60);
            template = CaptchaNoticeTemplate.restPasswordSms(bo.getLanguage(), code, timeout);
        } else if (!StringUtils.isEmpty(bo.getMail())) {
            unionId = bo.getMail();
            type = NoticeType.MAIL;
            code = captchaService.generateCaptchaInPermitTime(CaptchaType.PASSWORD_REST, bo.getMail(), timeout * 60, 60);
            template = CaptchaNoticeTemplate.restPasswordMail(bo.getLanguage(), code, timeout);
        } else {
            log.info("captcha error! param:{}", bo.toString());
            return false;
        }
        bo.setType(type.getCode());
        // 发送失败，删除缓存的captcha
        boolean result = false;
        try {
            result = captchaService.captchaPush(bo, template, code);
        } finally {
            if (!result) {
                captchaService.removeCaptchaFromCache(CaptchaType.PASSWORD_REST, unionId);
            }
        }
        return result;
    }

    @Override
    public boolean resetPassword(ResetPasswordBo bo) {
        String unionId;
        if (!StringUtils.isEmpty(bo.getPhone()) && !StringUtils.isEmpty(bo.getCountryCode())) {
            unionId = bo.getCountryCode() + bo.getPhone();
        } else if (!StringUtils.isEmpty(bo.getMail())) {
            unionId = bo.getMail();
        } else {
            log.info("resetPassword error! param:{}", bo.toString());
            throw new ServiceLogicException(PARAM_ERROR);
        }
        // 判断是否错误验证次数上限
        if (!captchaService.captchaValidateLimit(unionId, 10)) {
            throw new ServiceLogicException(CAPTCHA_LIMIT);
        }
        boolean result = captchaService.captchaValidate(CaptchaType.PASSWORD_REST, unionId, bo.getCode());
        if (!result) {
            log.error("captcha validate failed! unionId:[{}] code:[{}]", unionId, bo.getCode());
            // 错误次数+1
            captchaService.captchaValidateErrorIncr(unionId);
            throw new ServiceLogicException(CAPTCHA_ERROR);
        }
        // 清除验证码
        captchaService.removeCaptchaFromCache(CaptchaType.PASSWORD_REST, unionId);
        // 清除错误次数限制
        captchaService.captchaValidateErrorClear(unionId);

        // 重置用户密码，手机号不需要加国家码
        if (!StringUtils.isEmpty(bo.getCountryCode()) && unionId.startsWith(bo.getCountryCode())) {
            unionId = unionId.substring(bo.getCountryCode().length());
        }
        result = userAbility.resetPassword(new ResetPasswordReq(unionId, bo.getPassword()));
        return result;
    }

    @Override
    public Boolean createUser(String spaceId, UserRegisteredRequest req, List<String> roleCodes) {
        //1、向云端注册用户得到用户id
        //
        String uid;
        try {
            UserToken token = userAbility.loginUser(req);
            uid = token.getUid();
        }catch (ConnectorResultException e){
            log.info("查询用户失败{}",e.getErrorInfo());
            User user = userAbility.registeredUser(req);
            uid = user.getUser_id();
        }

        //2、向基础服务注册用户
        Boolean res = idaasUserAbility.createUser(spaceId, IdaasUserCreateReq.builder()
                .uid(uid)
                .username(req.getUsername())
                .remark("").build());
        if (!res) {
            throw new ServiceLogicException(USER_CREATE_FAIL);
        }
        //3、给用户授权
        Boolean auth = grantAbility.setRolesToUser(UserGrantRolesReq.builder()
                .spaceId(spaceId)
                .roleCodeList(roleCodes)
                .uid(uid).build());
        if (!auth) {
            throw new ServiceLogicException(USER_CREATE_FAIL);
        }
        //授权资产
        RoleTypeEnum roleTypeEnum = roleService.userOperateRole(spaceId, uid, roleCodes);
        if (RoleTypeEnum.normal.lt(roleTypeEnum)) {
            auth = auth && assetService.grantAllAsset(uid);
        }
        return res && auth;
    }

    @Override
    public Boolean updateUser(String spaceId, String operatUserId, String uid, String nickName, List<String> roleCodes) {
        if (!StringUtils.isEmpty(nickName)) {
            //修改昵称 TODO 等待云端开放

        }
        if (CollectionUtils.isEmpty(roleCodes)) {
            return true;
        }
        //修改角色
        RoleTypeEnum operatorRoleType = roleService.userOperateRole(spaceId, operatUserId);
        for (String roleCode : roleCodes) {
            if (operatorRoleType.lt(RoleTypeEnum.fromRoleCode(roleCode))) {
                throw new ServiceLogicException(ErrorCode.NO_DATA_PERMISSION);
            }
        }
        roleCodes = roleService.checkAndRemoveOldRole(spaceId, uid, roleCodes, true);
        if (roleCodes.size() > 0) {
            Boolean auth = grantAbility.setRolesToUser(UserGrantRolesReq.builder()
                    .spaceId(spaceId)
                    .roleCodeList(roleCodes)
                    .uid(uid).build());
            if (!auth) {
                throw new ServiceLogicException(USER_CREATE_FAIL);
            }
        }
        return true;
    }

    @Override
    public Boolean deleteUser(String spaceId, String uid) {
        //向云端删除用户
        Boolean del = userAbility.destroyUser(uid);
        if (!del) {
            throw new ServiceLogicException(USER_DELETE_FAIL, uid);
        }
        //向基础服务删除用户
        del = idaasUserAbility.deleteUser(spaceId, uid);
        if (!del) {
            throw new ServiceLogicException(USER_DELETE_FAIL, uid);
        }
        return del;
    }

    @Override
    public IdaasUser getUserByUid(String spaceId, String uid) {
        return null;
    }

    @Override
    public Boolean updateUserPassword(String userName, String newPwd) {
        return userAbility.resetPassword(new ResetPasswordReq(userName, newPwd));
    }

    @Override
    public Boolean batchDeleteUser(String spaceId, String... userIds) {
        if (userIds == null || userIds.length < 1) {
            throw new ServiceLogicException(PARAM_LOST, "userId");
        }
        if (userIds.length > 20) {
            throw new ServiceLogicException(USER_DELETE_COUNT_CAN_NOT_MORE_THAN_20);
        }
        for (String userId : userIds) {
            deleteUser(spaceId, userId);
        }
        return true;
    }

    @Override
    public PageVO<UserBaseInfo> queryUserByPage(String spaceId, String searchKey, String roleCode, Integer pageNo, Integer pageSize) {
        IdaasPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder()
                .roleCode(roleCode)
                .username(searchKey)
                .pageSize(pageSize)
                .pageNumber(pageNo)
                .build());
        PageVO<UserBaseInfo> result = new PageVO<>();
        result.setPageNo(pageResult.getPageNumber());
        result.setPageSize(pageResult.getPageSize());
        result.setData(pageResult.getResults().stream().map(e -> {
            List<IdaasRole> userRoles = roleAbility.queryRolesByUser(spaceId, e.getUid());
            return UserBaseInfo.builder()
                    .userName(e.getUsername())
                    .userId(e.getUid())
                    .roles(userRoles)
                    .createTime(e.getGmt_create())
                    .build();
        }).collect(Collectors.toList()));
        result.setTotal(pageResult.getTotalCount());
        return result;
    }

    @Override
    public List<IdaasUser> queryAdmins(String spaceId) {
        IdaasPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder()
                .roleCode(RoleTypeEnum.admin.name())
                .pageNumber(1)
                .pageSize(100)
                .build());
        if (pageResult != null && pageResult.getTotalCount() > 0) {
            return pageResult.getResults();
        }
        return new ArrayList<>();
    }
}

