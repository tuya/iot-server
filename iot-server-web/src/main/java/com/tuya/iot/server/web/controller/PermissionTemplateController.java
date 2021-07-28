package com.tuya.iot.server.web.controller;

import com.tuya.iot.server.core.constant.Response;
import com.tuya.iot.server.core.util.ContextUtil;
import com.tuya.iot.server.service.dto.PermissionNodeDTO;
import com.tuya.iot.server.service.idaas.PermissionTemplateService;
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
    PermissionTemplateService permissionTemplateService;

    @ApiOperation("查询模板权限森林")
    @GetMapping("/permission-template/role")
    public Response<List<PermissionNodeDTO>> listTemplateTrees(@RequestParam  String roleCode){
        String lang = ContextUtil.getLanguageOrDefault();
        log.info("查询模板权限森林入参:roleCode={},lang={}",roleCode,lang);
        List<PermissionNodeDTO> trees = permissionTemplateService.getTemplatePermissionTrees(roleCode,lang);
        log.info("查询模板权限森林出参:trees.size={}",trees.size());
        return Response.buildSuccess(trees);
    }
}
