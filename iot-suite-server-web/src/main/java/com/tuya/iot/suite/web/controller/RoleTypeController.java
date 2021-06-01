package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.Todo;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import com.tuya.iot.suite.web.model.RoleTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@RestController
@RequestMapping("/role-types")
@Slf4j
@Api(value = "角色类型")
public class RoleTypeController {
    @ApiOperation("查询角色类型")
    @GetMapping
    public Response<List<RoleTypeVO>> listRoleTypes() {
        return Response.buildSuccess(Stream.of(RoleTypeEnum.values()).map(it->RoleTypeVO.builder()
                .code(it.name()).name(it.name())
            .build()
        ).collect(Collectors.toList()));
    }

}
