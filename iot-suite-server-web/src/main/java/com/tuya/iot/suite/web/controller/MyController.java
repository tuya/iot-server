package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.core.util.*;
import com.tuya.iot.suite.service.dto.AssetVO;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.user.model.CaptchaPushBo;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.model.PermissionVO;
import com.tuya.iot.suite.web.model.ResetPasswordReq;
import com.tuya.iot.suite.web.model.RoleVO;
import com.tuya.iot.suite.web.model.criteria.UserCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.tuya.iot.suite.core.constant.ErrorCode.*;
import static com.tuya.iot.suite.core.constant.ErrorCode.USER_NOT_EXIST;

/**
 * @description: 用户个人数据接口
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@RestController
@RequestMapping("/my")
@Slf4j
@Api(value = "我的")
public class MyController {

    @Autowired
    private I18nMessage i18nMessage;

    @Autowired
    private UserService userService;

    @ApiOperation("我的权限列表")
    @GetMapping("/permissions")
    public Response<PermissionVO> myPermissions() {
        return Todo.todo();
    }

    @ApiOperation("我的角色列表")
    @GetMapping("/roles")
    public Response<RoleVO> myRoles() {
        return Todo.todo();
    }

    @ApiOperation("我的资产树")
    @GetMapping("/assets-tree")
    public Response<AssetVO> myAssetsTree() {
        return Todo.todo();
    }


    /**
     * @return
     */
    @ApiOperation(value = "修改密码")
    @SneakyThrows
    @PutMapping(value = "/password")
    public Response modifyLoginPassword(@RequestBody UserCriteria criteria) {
        String currentPassword = criteria.getCurrent_password();
        String newPassword = criteria.getNew_password();
        Boolean modifyLoginPassword = userService.modifyLoginPassword(ContextUtil.getUserId(), currentPassword, newPassword);
        return modifyLoginPassword ? Response.buildSuccess(true) :
                Response.buildFailure(USER_NOT_EXIST.getCode(), i18nMessage.getMessage(USER_NOT_EXIST.getCode(), USER_NOT_EXIST.getMsg()));
    }

    @ApiOperation(value = "获取密码重置验证码")
    @PostMapping(value = "/password/reset/captcha")
    public Response<Boolean> restPasswordCaptcha(@RequestBody ResetPasswordReq req) {
        String principal = SecurityUtils.getSubject().getPrincipal().toString();
        if(UserNameUtil.isEmail(principal)){
            req.setMail(principal);
        }else{
            req.setPhone(principal);
        }
        resetPasswordCheck(req);
        CaptchaPushBo captchaPushBo = new CaptchaPushBo();
        captchaPushBo.setLanguage(req.getLanguage());
        captchaPushBo.setCountryCode(req.getCountryCode());
        captchaPushBo.setPhone(req.getPhone());
        captchaPushBo.setMail(req.getMail());
        return Response.buildSuccess(userService.sendRestPasswordCaptcha(captchaPushBo));
    }
    @ApiOperation(value = "用户密码重置")
    @PostMapping(value = "/password/reset")
    public Response<Boolean> resetPassword(@RequestBody @Valid ResetPasswordReq req) {
        String principal = SecurityUtils.getSubject().getPrincipal().toString();
        if(UserNameUtil.isEmail(principal)){
            req.setMail(principal);
        }else{
            req.setPhone(principal);
        }
        // 参数校验
        resetPasswordCheck(req);
        if (StringUtils.isEmpty(req.getCode()) || StringUtils.isEmpty(req.getNewPassword())) {
            return Response.buildFailure(PARAM_ERROR);
        }
        ResetPasswordBo resetPasswordBo = new ResetPasswordBo();
        resetPasswordBo.setLanguage(req.getLanguage());
        resetPasswordBo.setMail(req.getMail());
        resetPasswordBo.setCountryCode(req.getCountryCode());
        resetPasswordBo.setPhone(req.getPhone());
        resetPasswordBo.setPassword(req.getNewPassword());
        resetPasswordBo.setCode(req.getCode());
        return Response.buildSuccess(userService.resetPassword(resetPasswordBo));
    }
    /**
     * 重置密码参数校验
     *
     * @param req
     */
    private void resetPasswordCheck(ResetPasswordReq req) {
        if (StringUtils.isEmpty(req.getMail())
                && StringUtils.isEmpty(req.getCountryCode())
                && StringUtils.isEmpty(req.getPhone())) {
            throw new ServiceLogicException(PARAM_ERROR);
        }
        // 验证手机格式
        if (!StringUtils.isEmpty(req.getCountryCode())
                && !StringUtils.isEmpty(req.getPhone())) {
            if (!LibPhoneNumberUtil.doValid(req.getPhone(), req.getCountryCode())) {
                log.info("telephone format error! =>{}{}", req.getCountryCode(), req.getPhone());
                throw new ServiceLogicException(TELEPHONE_FORMAT_ERROR);
            }
        }
        // 验证邮箱格式
        if (!StringUtils.isEmpty(req.getMail()) && !MixUtil.mailFormatValidate(req.getMail())) {
            log.info("mail format error! => [{}]", req.getMail());
            throw new ServiceLogicException(PARAM_ERROR);
        }
    }
}
