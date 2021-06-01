package com.tuya.iot.suite.web.controller;

import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.service.model.PageVO;
import com.tuya.iot.suite.service.model.RoleCodeGenerator;
import com.tuya.iot.suite.service.model.RoleTypeEnum;
import com.tuya.iot.suite.web.config.ProjectProperties;
import com.tuya.iot.suite.web.model.RoleVO;
import com.tuya.iot.suite.web.model.request.role.RoleAddReq;
import com.tuya.iot.suite.web.model.request.role.RoleEditReq;
import com.tuya.iot.suite.web.model.request.role.RolePermissionReq;
import com.tuya.iot.suite.web.model.response.permission.PermissionDto;
import com.tuya.iot.suite.web.util.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    @PostMapping("/roles")
    public Response<Boolean> createRole(@RequestBody RoleAddReq req) {
        String uid = SessionContext.getUserToken().getUserId();
        Boolean res = roleService.createRole(projectProperties.getSpaceId(), RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(req.getRoleType()))
                .roleName(req.getRoleName())
                .uid(uid)
                .build());
        return Response.buildSuccess(res);
    }

    @ApiOperation("角色列表")
    @GetMapping("/roles")
    public Response<PageVO<RoleVO>> listRoles(Integer pageNo,Integer pageSize,String roleCode,String roleName) {
        PageVO<IdaasRole> pageVO = roleService.queryRolesPagination(projectProperties.getSpaceId(),
                RolesPaginationQueryReq.builder()
                        .pageNum(pageNo)
                        .pageSize(pageSize)
                        .roleCode(roleCode)
                        .roleName(roleName)
                        .build());
        return Response.buildSuccess(PageVO.builder().pageNo(pageVO.getPageNo())
                .pageSize(pageVO.getPageSize())
                .total(pageVO.getTotal())
                .data((List)pageVO.getData().stream().map(
                        it->
                                RoleVO.builder().typeCode(RoleTypeEnum.fromRoleCode(it.getRoleCode()).name())
                        .code(it.getRoleCode())
                        .name(it.getRoleName())
                        .build()
                ).collect(Collectors.toList())).build());
    }

    @ApiOperation("修改角色")
    @PutMapping("/roles")
    public Response<Boolean> updateRoleName(@RequestBody RoleEditReq req) {
        Boolean res = roleService.updateRole(projectProperties.getSpaceId(), req.getRoleCode(),
                RoleUpdateReq.builder().roleName(req.getRoleName()).build());
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
