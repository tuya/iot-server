package com.tuya.iot.suite.web.controller;


import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.service.asset.AssetService;
import com.tuya.iot.suite.service.dto.AssetConvertor;
import com.tuya.iot.suite.service.dto.DeviceDTO;
import com.tuya.iot.suite.service.model.PageDataVO;
import com.tuya.iot.suite.web.model.DeviceInfoVO;
import com.tuya.iot.suite.web.model.UserToken;
import com.tuya.iot.suite.web.model.convert.DeviceInfoConvert;
import com.tuya.iot.suite.web.model.criteria.AssetCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/9
 */
@RequestMapping("/assets")
@RestController
@Api(description = "资产管理")
public class AssetController {

    @Resource
    private AssetService assetService;

    @Resource
    private HttpSession session;

    @ApiOperation(value = "添加资产")
    @PostMapping
    Response addAsset(@RequestBody AssetCriteria criteria) {
        String userId = getUserId(session);
        return assetService.addAsset(criteria.getAsset_name(), criteria.getParent_asset_id(), userId);
    }

    /**
     * 获取用户ID
     *
     * @param session
     * @return 返回当前登录用户ID
     */
    private String getUserId(HttpSession session) {
        UserToken userToken = (UserToken) session.getAttribute("token");
        if (!Objects.isNull(userToken)) {
            return userToken.getUserId();
        }
        return null;
    }

    @ApiOperation(value = "更新资产信息")
    @PutMapping(value = "/{asset_id}")
    Response updateAsset(@PathVariable("asset_id") String assetId, @RequestBody AssetCriteria criteria) {
        return assetService.updateAsset(getUserId(session), assetId, criteria.getAsset_name());
    }

    @ApiOperation("删除资产")
    @DeleteMapping(value = "/{asset_id}")
    Response deleteAsset(@PathVariable("asset_id") String assetId) {
        return assetService.deleteAsset(getUserId(session), assetId);
    }

    @ApiOperation(value = "获取树形资产结构")
    @GetMapping(value = "/tree/{asset_id}")
    Response getTreeBy(@PathVariable("asset_id") String assetId) {
        String userId = getUserId(session);
        return new Response(AssetConvertor.$.toAssetVO(assetService.getTreeByV2(assetId, userId)));
    }

    @ApiOperation(value = "通过资产名称获取资产列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "asset_name", value = "资产名称", required = true, paramType = "string")
    })
    @GetMapping
    Response getAssetByName(@RequestParam("asset_name") String assetName) {
        String userId = getUserId(session);
        return new Response(AssetConvertor.$.toAssetVOList(assetService.getAssetByNameV2(assetName, userId)));
    }

    @ApiOperation(value = "通过资产ID获取子资产")
    @GetMapping(value = "/{asset_id}")
    Response getChildAssetListBy(@PathVariable("asset_id") String assetId) {
        return new Response(AssetConvertor.$.toAssetVOList(assetService.getChildAssetListBy(assetId)));
    }

    @ApiOperation(value = "通过资产ID获取下级设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页数", defaultValue = "1", paramType = "int"),
            @ApiImplicitParam(name = "page_size", value = "页面大小", defaultValue = "10", paramType = "string")
    })
    @GetMapping(value = "/{asset_id}/deviceInfos")
    public Response<PageDataVO> getDeviceInfoBy(@PathVariable("asset_id") String assetId,
                                                @RequestParam(value = "page_no", required = false, defaultValue = "1") Integer pageNo,
                                                @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        PageDataVO<DeviceInfoVO> vo = new PageDataVO<>();
        vo.setPage_no(pageNo);
        vo.setPage_size(pageSize);
        PageDataVO<DeviceDTO> resultPage = assetService.getChildDeviceInfoPage(assetId, pageNo, pageSize);
        vo.setTotal(resultPage.getTotal());
        vo.setData(DeviceInfoConvert.INSTANCE.deviceDTO2VO(resultPage.getData()));
        return Response.buildSuccess(vo);
    }
}
