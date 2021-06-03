package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.ability.user.model.MobileCountries;
import com.tuya.iot.suite.ability.user.model.UserRegisteredRequest;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.exception.ServiceLogicException;
import com.tuya.iot.suite.core.util.ContextUtil;
import com.tuya.iot.suite.core.util.LibPhoneNumberUtil;
import com.tuya.iot.suite.core.util.MixUtil;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.model.PageVO;
import com.tuya.iot.suite.service.user.UserService;
import com.tuya.iot.suite.service.user.model.ResetPasswordBo;
import com.tuya.iot.suite.web.config.ProjectProperties;
import com.tuya.iot.suite.web.i18n.I18nMessage;
import com.tuya.iot.suite.web.model.ResetPasswordReq;
import com.tuya.iot.suite.web.model.request.user.BatchUserGrantRoleReq;
import com.tuya.iot.suite.web.model.request.user.UserAddReq;
import com.tuya.iot.suite.web.model.request.user.UserEditReq;
import com.tuya.iot.suite.web.model.request.user.UserPasswordModifyReq;
import com.tuya.iot.suite.web.model.request.user.UserPwdReq;
import com.tuya.iot.suite.web.model.response.permission.PermissionDto;
import com.tuya.iot.suite.web.model.response.user.UserDto;
import com.tuya.iot.suite.web.util.Responses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.tuya.iot.suite.core.constant.ErrorCode.*;


/**
 * @author bade, benguan
 */
@RestController
@Slf4j
@Api(value = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private I18nMessage i18nMessage;

    @Autowired
    private ProjectProperties projectProperties;

    @Autowired
    private GrantService grantService;

    /**
     * @return
     */
    @ApiOperation(value = "修改密码")
    @SneakyThrows
    @PutMapping(value = "/user/password")
    @RequiresPermissions("4005")
    public Response<Boolean> modifyLoginPassword(@RequestBody UserPasswordModifyReq req) {
        Boolean modifyLoginPassword = userService.modifyLoginPassword(req.getUid(), req.getOldPassword(), req.getNewPassword());
        return modifyLoginPassword ? Response.buildSuccess(true) :
                Responses.buildFailure(USER_NOT_EXIST);
    }

    /** 这个还需要吗？要验证码的 */
    @ApiOperation(value = "用户密码重置")
    @PostMapping(value = "/user/password/reset")
    public Response<Boolean> resetPassword(@RequestBody @Valid ResetPasswordReq req) {
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

    @ApiOperation(value = "获取手机号国际区号")
    @GetMapping(value = "/mobile/countries")
    public Response<MobileCountries> selectMobileCountries() {
        return new Response<>(userService.selectMobileCountries());
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
            // 国家码转换
            req.setCountryCode(Integer.valueOf(req.getCountryCode()).toString());
        }
        // 验证邮箱格式
        if (!StringUtils.isEmpty(req.getMail()) && !MixUtil.mailFormatValidate(req.getMail())) {
            log.info("mail format error! => [{}]", req.getMail());
            throw new ServiceLogicException(PARAM_ERROR);
        }
    }

    @ApiOperation("创建用户")
    @PostMapping("/users")
    @RequiresPermissions("4002")
    public Response<Boolean> createUser(@RequestBody UserAddReq req) {
        Long spaceId = 0L;
        if (CollectionUtils.isEmpty(req.getRoleCodes())) {
            throw new ServiceLogicException(PARAM_LOST);
        }
        return Response.buildSuccess(userService.createUser(spaceId, UserRegisteredRequest.builder()
                .username(req.getUserName())
                .password(req.getPassword())
                .country_code(req.getCountryCode())
                .nicke_name(req.getNickName())
                .build(), req.getRoleCodes()));
    }

    @ApiOperation("修改用户")
    @PutMapping("/users")
    @RequiresPermissions("4003")
    public Response<Boolean> updateUserName(@RequestBody UserEditReq req) {
        Long spaceId = 0L;
        return Response.buildSuccess(userService.updateUser(spaceId,req.getUserId(),req.getNickName(),req.getRoleCodes()));
    }

    @ApiOperation("修改用户密码")
    @PutMapping("/users/pwd")
    @RequiresPermissions("4005")
    public Response<Boolean> updateUserPwd(@RequestBody UserPwdReq req) {
        return Response.buildSuccess(userService.updateUserPassword(req.getUserName(),req.getNewPwd()));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/users/{userId}")
    @RequiresPermissions("4004")
    public Response<Boolean> updateUserPwd(@PathVariable("userId") String userId) {
        Long spaceId = 0L;
        return Response.buildSuccess(userService.deleteUser(spaceId,userId));
    }

    /**
     * 批量操作，统一按"resources-batch"方式定义路径。（谁有好的建议可以提出来）
     */
    @ApiOperation("批量删除用户")
    @DeleteMapping("/users")
    @RequiresPermissions("4003")
    public Response<Boolean> batchDeleteUser(@ApiParam(value = "uid列表，逗号分隔", required = true)
                                             @RequestParam String uidList) {
        Long spaceId = 0L;
        return Response.buildSuccess(userService.batchDeleteUser(spaceId,uidList.split(",")));
    }

    @ApiOperation("用户列表")
    @GetMapping("/users")
    @RequiresPermissions("4001")
    public Response<PageVO<UserDto>> listUsers(@ApiParam(value = "搜索关键字")@RequestParam String searchKey,
                                               @ApiParam(value = "角色编码")@RequestParam String roleCode) {
        return Todo.todo();
    }

    @ApiOperation("用户权限列表")
    @GetMapping("/users/{uid}/permissions")
    @RequiresPermissions("4001")
    public Response<List<PermissionDto>> listUserPermissions(
            @ApiParam(value = "用户id") @PathVariable String uid) {
        return Todo.todo();
    }

    @ApiOperation("批量用户角色授权")
    @PutMapping("/users/roles")
    @RequiresPermissions("4003")
    public Response<Boolean> grantRole(@RequestBody BatchUserGrantRoleReq req){
        Long spaceId = projectProperties.getPermissionSpaceId();
        String uid = ContextUtil.getUserId();
        return Response.buildSuccess(grantService.setRoleToUsers(spaceId,uid,req.getRoleCode(),req.getUserIds()));
    }
}
