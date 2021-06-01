package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.ability.idaas.model.IdaasRoleCreateReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.SuiteRoleCode;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.web.config.ProjectProperties;
import com.tuya.iot.suite.web.model.RoleCreateReq;
import com.tuya.iot.suite.web.model.RoleNameUpdateReq;
import com.tuya.iot.suite.web.model.RoleVO;
import com.tuya.iot.suite.web.util.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController
@Slf4j
@Api(value = "角色管理")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    ProjectProperties projectProperties;

    @ApiOperation("创建角色")
    @PutMapping("/roles")
    public Response<Boolean> createRole(@RequestBody RoleCreateReq req) {
        String uid = SessionContext.getUserToken().getUserId();
        //TODO 校验角色类型，不能创建超级管理员类型的角色
        Boolean res = roleService.createRole(projectProperties.getSpaceId(), IdaasRoleCreateReq.builder()
                .roleCode(
                        SuiteRoleCode.builder()
                                .code(SuiteRoleCode.randomCode())
                                .type(req.getRoleType())
                                .build()
                                .toCloudRoleCode()
                )
                .roleName(req.getRoleName())
                .build());
        return Response.buildSuccess(res);
    }

    @ApiOperation("角色列表")
    @GetMapping("/roles")
    public Response<List<RoleVO>> listRoles() {
        //TODO
        //需要iot平台提供一个查询所有角色的接口
        //roleService.queryRolesByUser(projectProperties.getSpaceId(),)
        return Todo.todo();
    }

    @ApiOperation("修改角色名称")
    @PutMapping("/roles/{roleCode}/name")
    public Response<Boolean> updateRoleName(@PathVariable String roleCode, @RequestBody RoleNameUpdateReq req) {
        Boolean res = roleService.updateRole(projectProperties.getSpaceId(), roleCode,
                RoleUpdateReq.builder().roleName(req.getName()).build());
        return Response.buildSuccess(res);
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/batch-roles")
    public Response<Boolean> batchDeleteRole(@ApiParam(value = "角色编码列表，逗号分隔", required = true)
                                             @RequestParam("roleCodeList") String roleCodeList) {
        Set<String> roleCodes = StringUtils.commaDelimitedListToSet(roleCodeList);
        boolean success = true;
        for (String roleCode : roleCodes) {
            success &= roleService.deleteRole(projectProperties.getSpaceId(),roleCode);
        }
        return Response.buildSuccess(success);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/roles/{roleCode}")
    public Response<Boolean> deleteRole(@PathVariable String roleCode) {
        Boolean success = roleService.deleteRole(projectProperties.getSpaceId(),roleCode);
        return Response.buildSuccess(success);
    }
}
