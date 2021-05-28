package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.web.model.RoleCreateReq;
import com.tuya.iot.suite.web.model.RoleNameUpdateReq;
import com.tuya.iot.suite.web.model.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController
@Slf4j
@Api(value = "角色管理")
public class RoleController {
    @ApiOperation("创建角色")
    @PutMapping("/roles")
    public Response<Boolean> createRole(@RequestBody RoleCreateReq req) {
        return Todo.todo();
    }

    @ApiOperation("角色列表")
    @GetMapping("/roles")
    public Response<List<RoleVO>> listRoles() {
        return Todo.todo();
    }

    @ApiOperation("修改角色名称")
    @PutMapping("/roles/{roleCode}/name")
    public Response<Boolean> updateRoleName(@PathVariable String roleCode, @RequestBody RoleNameUpdateReq req) {
        return Todo.todo();
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/batch-roles")
    public Response<Boolean> batchDeleteRole(@ApiParam(value = "角色编码列表，逗号分隔") @RequestParam("roleCodeList") String roleCodeList) {
        return Todo.todo();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/roles/{roleCode}")
    public Response<Boolean> deleteRole(@PathVariable String roleCode) {
        return Todo.todo();
    }
}
