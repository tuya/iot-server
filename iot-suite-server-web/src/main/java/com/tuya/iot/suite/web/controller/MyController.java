package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.dto.AssetVO;
import com.tuya.iot.suite.web.model.PermissionVO;
import com.tuya.iot.suite.web.model.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation("我的权限列表")
    @GetMapping("/permissions")
    public Response<PermissionVO> myPermissions(){
        return Todo.todo();
    }
    @ApiOperation("我的角色列表")
    @GetMapping("/roles")
    public Response<RoleVO> myRoles(){
        return Todo.todo();
    }
    @ApiOperation("我的资产树")
    @GetMapping("/assets-tree")
    public Response<AssetVO> myAssetsTree(){
        return Todo.todo();
    }

}
