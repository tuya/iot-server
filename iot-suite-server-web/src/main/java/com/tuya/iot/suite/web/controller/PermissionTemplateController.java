package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.web.model.PermissionTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController
@RequestMapping("/permission-templates")
@Slf4j
@Api(value = "权限模板")
public class PermissionTemplateController {

    /**
     * 是否需要权限模板？
     * 可以创建几个角色，当作权限模板。
     * 创建其他角色，可以选择另一个角色当作源，创建其副本。
     * 这样不仅可以实现需求，而且更灵活。管控台也不需要保存模板文件了。
     * */
    @ApiOperation("查询权限模版列表")
    @GetMapping
    public Response<List<PermissionTemplate>> listPermTemplates(){
        return Todo.todo();
    }

}
