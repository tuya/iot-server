package com.tuya.iot.suite.web.controller;

import com.google.common.collect.Lists;
import com.tuya.iot.suite.ability.idaas.model.IdaasRole;
import com.tuya.iot.suite.ability.idaas.model.PermissionQueryByRolesReq;
import com.tuya.iot.suite.ability.idaas.model.PermissionTypeEnum;
import com.tuya.iot.suite.ability.idaas.model.RoleGrantPermissionsReq;
import com.tuya.iot.suite.ability.idaas.model.RoleUpdateReq;
import com.tuya.iot.suite.ability.idaas.model.RolesPaginationQueryReq;
import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.ContextUtil;
import com.tuya.iot.suite.service.dto.RoleCreateReqDTO;
import com.tuya.iot.suite.service.idaas.GrantService;
import com.tuya.iot.suite.service.idaas.PermissionService;
import com.tuya.iot.suite.service.idaas.RoleService;
import com.tuya.iot.suite.core.model.PageVO;
import com.tuya.iot.suite.service.util.RoleCodeGenerator;
import com.tuya.iot.suite.web.config.ProjectProperties;
import com.tuya.iot.suite.web.model.RoleVO;
import com.tuya.iot.suite.web.model.request.role.RoleAddReq;
import com.tuya.iot.suite.web.model.request.role.RoleEditReq;
import com.tuya.iot.suite.web.model.request.role.RolePermissionReq;
import com.tuya.iot.suite.web.model.response.permission.PermissionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @Autowired
    GrantService grantService;
    @Autowired
    PermissionService permissionService;

    @ApiOperation("创建角色")
    @PostMapping("/roles")
    @RequiresPermissions("3002")
    public Response<Boolean> createRole(@RequestBody RoleAddReq req) {
        log.info("创建角色入参:{}",req);
        String uid = ContextUtil.getUserId();
        Boolean res = roleService.createRole(projectProperties.getPermissionSpaceId(), RoleCreateReqDTO.builder()
                .roleCode(RoleCodeGenerator.generate(req.getRoleType()))
                .roleName(req.getRoleName())
                .remark(req.getRoleRemark())
                .uid(uid)
                .build());
        log.info("创建角色出参:{}",res);
        return Response.buildSuccess(res);
    }

    @ApiOperation("角色列表")
    @GetMapping("/roles")
    @RequiresPermissions("3001")
    public Response<PageVO<RoleVO>> listRoles(Integer pageNo, Integer pageSize, String roleCode, String roleName) {
        log.info("查询角色列表入参:pageNo={},pageSize={},roleCode={},roleName={}",pageNo,pageSize,roleCode,roleName);
        PageVO<IdaasRole> pageVO = roleService.queryRolesPagination(projectProperties.getPermissionSpaceId(),
                RolesPaginationQueryReq.builder()
                        .pageNumber(pageNo)
                        .pageSize(pageSize)
                        .roleCode(roleCode)
                        .roleName(roleName)
                        .gmtModifiedAsc(true)
                        .build());
        PageVO<RoleVO> pageVo = PageVO.builder().pageNo(pageVO.getPageNo())
                .pageSize(pageVO.getPageSize())
                .total(pageVO.getTotal())
                .data((List) pageVO.getData().stream().map(
                        it ->
                                RoleVO.builder()
                                        .roleCode(it.getRoleCode())
                                        .roleName(it.getRoleName())
                                        .remark(it.getRemark())
                                        .build()
                ).collect(Collectors.toList())).build();
        log.info("查询角色列表出参:total={},data.size={}",pageVo.getTotal(),pageVo.getData().size());
        return Response.buildSuccess(pageVo);
    }

    @ApiOperation("修改角色")
    @PutMapping("/roles")
    @RequiresPermissions("3003")
    public Response<Boolean> updateRoleName(@RequestBody RoleEditReq req) {
        log.info("修改角色入参:{}",req);
        Boolean res = roleService.updateRole(projectProperties.getPermissionSpaceId(),
                ContextUtil.getUserId(),
                req.getRoleCode(),
                RoleUpdateReq.builder().roleName(req.getRoleName())
                        .remark(req.getRoleRemark())
                        .build());
        log.info("修改角色出参:{}",res);
        return Response.buildSuccess(res);
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/batch-roles")
    @RequiresPermissions("3004")
    public Response<Boolean> batchDeleteRole(@ApiParam(value = "角色编码列表，逗号分隔", required = true)
                                             @RequestParam("roleCodeList") String roleCodeList) {
        log.info("批量删除角色入参:roleCodeList={}",roleCodeList);
        Set<String> roleCodes = StringUtils.commaDelimitedListToSet(roleCodeList);
        boolean success = roleService.deleteRoles(projectProperties.getPermissionSpaceId(), ContextUtil.getUserId(), roleCodes);
        log.info("批量删除角色出参:{}", success);
        return Response.buildSuccess(success);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/roles/{roleCode}")
    @RequiresPermissions("3004")
    public Response<Boolean> deleteRole(@PathVariable String roleCode) {
        log.info("删除角色入参:roleCode={}",roleCode);
        Boolean success = roleService.deleteRole(projectProperties.getPermissionSpaceId(),
                ContextUtil.getUserId(),
                roleCode);
        log.info("删除角色出参:{}",success);
        return Response.buildSuccess(success);
    }

    @ApiOperation("给角色授权")
    @PutMapping("/roles/permissions")
    @RequiresPermissions("3005")
    public Response<Boolean> rolePermissions(@RequestBody RolePermissionReq req) {
        log.info("角色授权入参:{}",req);
        Boolean success = grantService.setPermissionsToRole(ContextUtil.getUserId(), RoleGrantPermissionsReq.builder()
                .spaceId(projectProperties.getPermissionSpaceId())
                .roleCode(req.getRoleCode())
                .permissionCodes(req.getPermissionCodes())
                .build());
        log.info("角色授权出参:{}",success);
        return Response.buildSuccess(success);
    }

    @ApiOperation("角色权限重置")
    @PutMapping("/roles/permissions/reset")
    @RequiresPermissions("3005")
    public Response<Boolean> resetRolePermissionsFromTemplate(@ApiParam(value = "角色编码",required = true) @RequestParam String roleCode) {
        log.info("角色权限重置入参:roleCode={}",roleCode);
        Boolean success = roleService.resetRolePermissionsFromTemplate(projectProperties.getPermissionSpaceId(),ContextUtil.getUserId(),roleCode);
        log.info("角色权限重置入参:{}",success);
        return Response.buildSuccess(success);
    }

    @ApiOperation("查角色拥有的授权")
    @GetMapping("/roles/permissions")
    @RequiresPermissions("3006")
    public Response<List<PermissionDto>> getRolePermissions(@RequestParam String roleCode) {
        log.info("查角色拥有的授权入参:roleCode={}",roleCode);
        List<PermissionDto> list = permissionService.queryPermissionsByRoleCodes(
                projectProperties.getPermissionSpaceId(),
                PermissionQueryByRolesReq.builder()
                        .roleCodeList(Lists.newArrayList(roleCode))
                        .build())
                .stream().flatMap(it ->
                        it.getPermissionList().stream()
                                .map(p ->
                                        PermissionDto.builder()
                                                .permissionCode(p.getPermissionCode())
                                                .permissionName(p.getName())
                                                .permissionType(PermissionTypeEnum.fromCode(p.getType()).name())
                                                .order(p.getOrder())
                                                .remark(p.getRemark())
                                                .parentCode(p.getParentCode())
                                                .build())
                ).collect(Collectors.toList());
        log.info("查角色拥有的授权出参:list.size={}",list.size());
        return Response.buildSuccess(list);
    }
}
