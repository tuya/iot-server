package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.model.PageVO;
import com.tuya.iot.suite.web.model.request.role.RoleAddReq;
import com.tuya.iot.suite.web.model.request.role.RoleEditReq;
import com.tuya.iot.suite.web.model.request.role.RolePermissionReq;
import com.tuya.iot.suite.web.model.response.permission.PermissionDto;
import com.tuya.iot.suite.web.model.response.role.RoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController
@Slf4j
@Api(value = "角色管理")
public class RoleController {


    @ApiOperation("角色列表")
    @GetMapping("/roles")
    public Response<PageVO<RoleDto>> lislistRolestRoles() {
        return Todo.todo();
    }

    @ApiOperation("创建角色")
    @PostMapping("/role")
    public Response<Boolean> createRole(@RequestBody RoleAddReq req) {
        return Todo.todo();
    }

    @ApiOperation("修改角色")
    @PutMapping("/role")
    public Response<Boolean> updateRoleName(@RequestBody RoleEditReq req) {
        return Todo.todo();
    }


    @ApiOperation("删除角色")
    @DeleteMapping("/role/{roleCode}")
    public Response<Boolean> deleteRole(@PathVariable String roleCode) {
        return Todo.todo();
    }

    @ApiOperation("给角色授权")
    @PutMapping("/role/permissions")
    @RequiresPermissions("roles")
    public Response<Boolean> rolePermissions(@RequestBody RolePermissionReq req) {
        return Todo.todo();
    }

    @ApiOperation("查角色拥有的授权")
    @GetMapping("/role/permissions")
    @RequiresPermissions("roles")
    public Response<List<PermissionDto>> getRolePermissions(@RequestParam String roleCode) {
        return Todo.todo();
    }
}
