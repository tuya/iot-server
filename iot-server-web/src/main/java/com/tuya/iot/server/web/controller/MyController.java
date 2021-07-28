package com.tuya.iot.server.web.controller;

import com.tuya.iot.server.core.util.ContextUtil;
import com.tuya.iot.server.core.util.LibPhoneNumberUtil;
import com.tuya.iot.server.core.util.MixUtil;
import com.tuya.iot.server.core.util.UserNameUtil;
import com.tuya.iot.server.web.i18n.I18nMessage;
import com.tuya.iot.server.web.model.criteria.UserCriteria;
import com.tuya.iot.server.web.util.ResponseI18n;
import com.tuya.iot.server.ability.idaas.model.PermissionTypeEnum;
import com.tuya.iot.server.core.constant.Response;
import com.tuya.iot.server.core.exception.ServiceLogicException;
import com.tuya.iot.server.service.asset.AssetService;
import com.tuya.iot.server.service.dto.PermissionNodeDTO;
import com.tuya.iot.server.service.idaas.PermissionService;
import com.tuya.iot.server.service.idaas.RoleService;
import com.tuya.iot.server.service.user.UserService;
import com.tuya.iot.server.service.user.model.CaptchaPushBo;
import com.tuya.iot.server.service.user.model.ResetPasswordBo;
import com.tuya.iot.server.web.config.ProjectProperties;
import com.tuya.iot.server.web.model.ResetPasswordReq;
import com.tuya.iot.server.web.model.RoleVO;
import com.tuya.iot.server.web.model.response.permission.PermissionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.tuya.iot.server.core.constant.ErrorCode.*;

/**
 * @description: 用户个人数据接口
 * 暂时没有提供出去（2020/06/16）
 * 从合理性上分析，应该是需要的。比如用户登陆后，需要查询自己有哪些权限。
 * 如果直接请求接口/users/{uid}/permissions，那么如果用户传其他用户的uid，就可以看到其他用户有哪些权限了。
 *
 * 原先没有权限管理的时候，没有区分 管理员操作 和 用户操作。
 * 管理员操作：
 * 管理用户、角色、权限、修改用户的密码等
 * 用户操作：
 * 查询自己的角色、权限、管理资产、修改自己的密码等
 *
 * 管理员同时又是用户，所以管理员也可以查询自己的角色、权限、管理资产、修改自己的密码
 * 所以必须将管理员的查询其他用户的角色、权限 和 用户查询自己的角色、权限区分
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@RestController
@RequestMapping("/my")
@Slf4j
@Api(value = "我的")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyController {

    @Autowired
    I18nMessage i18nMessage;

    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProjectProperties projectProperties;

    @Autowired
    AssetService assetService;

    @ApiOperation("我的权限列表")
    @GetMapping("/permissions")
    public Response<List<PermissionDto>> myPermissions() {
        log.info("查询我的权限列表入参:无");
        String uid = ContextUtil.getUserId();
        List<PermissionDto> perms = permissionService.queryPermissionsByUser(projectProperties.getPermissionSpaceId(), uid)
                .stream()
                .map(it ->
                        PermissionDto.builder()
                                .permissionCode(it.getPermissionCode())
                                .permissionName(it.getName())
                                .permissionType(PermissionTypeEnum.fromCode(it.getType()).name())
                                .remark(it.getRemark())
                                .order(it.getOrder())
                                .build())
                .collect(Collectors.toList());
        log.info("查询我的权限列表出参:perms.size={}",perms.size());
        return Response.buildSuccess(perms);
    }

    @ApiOperation("我的权限树")
    @GetMapping("/permissions-trees")
    public Response<List<PermissionNodeDTO>> myPermissionsTrees() {
        log.info("查询我的权限树入参:无");
        String uid = ContextUtil.getUserId();
        List<PermissionNodeDTO> trees = permissionService.queryPermissionTrees(projectProperties.getPermissionSpaceId(), uid);
        log.info("查询我的权限列表出参:trees.size={}",trees.size());
        return Response.buildSuccess(trees);
    }

    @ApiOperation("我的角色列表")
    @GetMapping("/roles")
    public Response<List<RoleVO>> myRoles() {
        log.info("查询我的角色列表入参:无}");
        String uid = ContextUtil.getUserId();
        List<RoleVO> list = roleService.queryRolesByUser(projectProperties.getPermissionSpaceId(), uid)
                .stream().map(it -> RoleVO.builder()
                        .roleCode(it.getRoleCode())
                        .roleName(it.getRoleName())
                        .build()
                ).collect(Collectors.toList());
        log.info("查询我的角色列表出参:list.size={}",list.size());
        return Response.buildSuccess(list);
    }

    /**
     * @return
     */
    @ApiOperation(value = "修改密码")
    @SneakyThrows
    @PutMapping(value = "/password")
    public Response modifyLoginPassword(@RequestBody UserCriteria criteria) {
        log.info("修改我的密码入参:略");
        String currentPassword = criteria.getCurrent_password();
        String newPassword = criteria.getNew_password();
        Boolean modifyLoginPassword = userService.modifyLoginPassword(ContextUtil.getUserId(), currentPassword, newPassword);
        log.info("修改我的密码出参:{}",modifyLoginPassword);
        return modifyLoginPassword ? ResponseI18n.buildSuccess(true) :
                ResponseI18n.buildFailure(USER_NOT_EXIST);
    }

    @ApiOperation(value = "获取密码重置验证码")
    @PostMapping(value = "/password/reset/captcha")
    public Response<Boolean> restPasswordCaptcha(@RequestBody ResetPasswordReq req) {
        log.info("重置我的密码获取验证码入参:略");
        String principal = SecurityUtils.getSubject().getPrincipal().toString();
        if (UserNameUtil.isEmail(principal)) {
            req.setMail(principal);
        } else {
            req.setPhone(principal);
        }
        resetPasswordCheck(req);
        CaptchaPushBo captchaPushBo = new CaptchaPushBo();
        captchaPushBo.setLanguage(req.getLanguage());
        captchaPushBo.setCountryCode(req.getCountryCode());
        captchaPushBo.setPhone(req.getPhone());
        captchaPushBo.setMail(req.getMail());
        boolean sendRes = userService.sendRestPasswordCaptcha(captchaPushBo);
        log.info("重置我的密码获取验证码出参:{}",sendRes);
        return Response.buildSuccess(sendRes);
    }

    @ApiOperation(value = "用户密码重置")
    @PostMapping(value = "/password/reset")
    public Response<Boolean> resetPassword(@RequestBody @Valid ResetPasswordReq req) {
        log.info("重置我的密码入参:略");
        String principal = SecurityUtils.getSubject().getPrincipal().toString();
        if (UserNameUtil.isEmail(principal)) {
            req.setMail(principal);
        } else {
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
        boolean res = userService.resetPassword(resetPasswordBo);
        log.info("重置我的密码出参:{}",res);
        return Response.buildSuccess(res);
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
