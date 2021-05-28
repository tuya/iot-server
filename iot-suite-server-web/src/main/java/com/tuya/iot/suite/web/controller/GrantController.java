package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.web.model.BatchRoleRelatePermissionReq;
import com.tuya.iot.suite.web.model.UserRelateAssetReq;
import com.tuya.iot.suite.web.model.UserRelateRoleReq;
import com.tuya.iot.suite.web.model.BatchUserRelateRoleReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController
@Slf4j
@Api(value = "授权管理")
public class GrantController {
    @ApiOperation("设置用户角色")
    @PutMapping("/grants/user-role")
    public Response<Boolean> relateUserWithRoles(@RequestBody UserRelateRoleReq req) {
        return Todo.todo();
    }

    @ApiOperation("用户资产授权")
    @PutMapping("/grants/user-asset")
    public Response<Boolean> relateUserWithAsserts(@RequestBody UserRelateAssetReq req) {
        return Todo.todo();
    }

    @ApiOperation("批量设置用户角色")
    @PutMapping("/batch-grants/user-role")
    public Response<Boolean> relateBatchUserWithRoles(@RequestBody BatchUserRelateRoleReq req) {
        return Todo.todo();
    }

    @ApiOperation("批量关联权限")
    @PutMapping("/batch-grants/role-permission")
    public Response<Boolean> relateBatchRoleWithPermissions(@RequestBody BatchRoleRelatePermissionReq req) {
        return Todo.todo();
    }

    @ApiOperation("重置角色权限")
    @PutMapping("/grants/role-permission/reset")
    public Response<Boolean> resetRolePermissions(@ApiParam(value = "角色编码", required = true)
                                                  @RequestParam String roleCode) {
        return Todo.todo();
    }
}
