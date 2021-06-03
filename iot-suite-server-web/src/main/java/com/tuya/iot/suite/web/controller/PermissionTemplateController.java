package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.service.dto.PermissionNodeDTO;
import com.tuya.iot.suite.service.idaas.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController

@Slf4j
@Api(value = "权限模板")
public class PermissionTemplateController {

    @Autowired
    RoleService roleService;

    @ApiOperation("查询权限模版列表")
    @GetMapping("/permission-template/role")
    public Response<List<PermissionNodeDTO>> listPermTemplates(@RequestParam  String roleCode){
        return Response.buildSuccess(roleService.getPermissionTemplate(roleCode).getChildren());
    }

}
