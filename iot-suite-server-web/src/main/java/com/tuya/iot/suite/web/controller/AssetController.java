package com.tuya.iot.suite.web.controller;


import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.core.util.ContextUtil;
import com.tuya.iot.suite.service.asset.AssetService;
import com.tuya.iot.suite.service.dto.AssetConvertor;
import com.tuya.iot.suite.service.dto.AssetVO;
import com.tuya.iot.suite.service.dto.DeviceDTO;
import com.tuya.iot.suite.service.model.PageDataVO;
import com.tuya.iot.suite.web.model.response.device.DeviceInfoVO;
import com.tuya.iot.suite.web.model.convert.DeviceInfoConvert;
import com.tuya.iot.suite.web.model.criteria.AssetCriteria;
import com.tuya.iot.suite.web.model.request.asset.AssetAuths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/9
 */
@RequestMapping("/assets")
@RestController
@Api(value = "资产管理")
public class AssetController {

    @Resource
    private AssetService assetService;

    @ApiOperation(value = "添加资产")
    @PostMapping
    @RequiresPermissions("1002")
    public Response addAsset(@RequestBody AssetCriteria criteria) {
        return assetService.addAsset(criteria.getAsset_name(), criteria.getParent_asset_id(), ContextUtil.getUserId());
    }

    @ApiOperation(value = "更新资产信息")
    @PutMapping(value = "/{asset_id}")
    @RequiresPermissions("1003")
    public Response updateAsset(@PathVariable("asset_id") String assetId, @RequestBody AssetCriteria criteria) {
        return assetService.updateAsset(ContextUtil.getUserId(), assetId, criteria.getAsset_name());
    }

    @ApiOperation("删除资产")
    @DeleteMapping(value = "/{asset_id}")
    @RequiresPermissions("1004")
    public Response deleteAsset(@PathVariable("asset_id") String assetId) {
        return assetService.deleteAsset(ContextUtil.getUserId(), assetId);
    }

    @ApiOperation(value = "获取树形资产结构")
    @GetMapping(value = "/tree/{asset_id}")
    @RequiresPermissions("1001")
    public Response<AssetVO> getTreeBy(@PathVariable("asset_id") String assetId) {
        return Response.buildSuccess(AssetConvertor.$.toAssetVO(assetService.getTreeByV2(assetId, ContextUtil.getUserId())));
    }

    @ApiOperation(value = "资产树接口")
    @GetMapping(value = "/tree-fast/{asset_id}")
    @RequiresPermissions("1001")
    public Response<List<AssetVO>> getTreeFastBy(@PathVariable("asset_id") String assetId) {
        return Response.buildSuccess(AssetConvertor.$.toAssetVOList(assetService.getTreeFast(assetId, ContextUtil.getUserId())));
    }

    @ApiOperation(value = "通过资产名称获取资产列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "asset_name", value = "资产名称", required = true, paramType = "string")
    })

    @GetMapping
    @RequiresPermissions("1001")
    public Response<List<AssetVO>> getAssetByName(@RequestParam("asset_name") String assetName) {
        return Response.buildSuccess(AssetConvertor.$.toAssetVOList(assetService.getAssetByNameV2(assetName, ContextUtil.getUserId())));
    }

    @ApiOperation(value = "通过资产ID获取子资产")
    @GetMapping(value = "/{asset_id}")
    @RequiresPermissions("1001")
    public Response<List<AssetVO>> getChildAssetListBy(@PathVariable("asset_id") String assetId) {
        return Response.buildSuccess(AssetConvertor.$.toAssetVOList(assetService.getTreeFast(assetId, ContextUtil.getUserId())));
    }

    @ApiOperation(value = "通过资产ID获取下级设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页数", defaultValue = "1", paramType = "int"),
            @ApiImplicitParam(name = "page_size", value = "页面大小", defaultValue = "10", paramType = "string")
    })
    @GetMapping(value = "/{asset_id}/deviceInfos")
    @RequiresPermissions("1001")
    public Response<PageDataVO<DeviceInfoVO>> getDeviceInfoBy(@PathVariable("asset_id") String assetId,
                                                @RequestParam(value = "page_no", required = false, defaultValue = "1") Integer pageNo,
                                                @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        PageDataVO<DeviceInfoVO> vo = new PageDataVO<>();
        vo.setPage_no(pageNo);
        vo.setPage_size(pageSize);
        PageDataVO<DeviceDTO> resultPage = assetService.getChildDeviceInfoPage(ContextUtil.getUserId(),assetId, pageNo, pageSize);
        vo.setTotal(resultPage.getTotal());
        vo.setData(DeviceInfoConvert.INSTANCE.deviceDTO2VO(resultPage.getData()));
        return Response.buildSuccess(vo);
    }


    @ApiOperation(value = "批量给用户授权资产")
    @PutMapping(value = "/auths")
    @RequiresPermissions("1005")
    public Response<Boolean> authAssetToUser(@RequestBody AssetAuths req) {
        return  Response.buildSuccess(assetService.authAssetToUser(req.getUserId(),req.getAssetIds()));
    }

    @ApiOperation(value = "用户被授权的资产")
    @GetMapping(value = "/auths")
    @RequiresPermissions("1005")
    public Response<List<AssetVO>> authAssetOfUser() {
        return Response.buildSuccess(AssetConvertor.$.toAssetVOList(assetService.getTreeByUser(ContextUtil.getUserId())));
    }

    @ApiOperation(value = "系统所有资产")
    @GetMapping(value = "/all")
    @RequiresPermissions("1005")
    public Response<List<AssetVO>> sysAssetAll() {
        String roleCode = "admin";
        return Response.buildSuccess(AssetConvertor.$.toAssetVOList(assetService.getAllTree(roleCode,ContextUtil.getUserId())));
    }


}
