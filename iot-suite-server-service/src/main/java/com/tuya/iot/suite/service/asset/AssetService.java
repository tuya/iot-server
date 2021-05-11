package com.tuya.iot.suite.service.asset;


import com.tuya.iot.suite.core.constant.Response;
import com.tuya.iot.suite.service.dto.AssetDTO;
import com.tuya.iot.suite.service.dto.DeviceDTO;
import com.tuya.iot.suite.service.model.PageDataVO;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/9
 */
public interface AssetService {

    /**
     * 添加资产
     *
     * @param assetName
     * @param parentAssetId
     * @return
     */
    Response addAsset(String assetName, String parentAssetId, String userId);

    /**
     * 更新资产
     *
     *
     * @param userId
     * @param assetId
     * @param assetName
     * @return
     */
    Response updateAsset(String userId, String assetId, String assetName);

    /**
     * 删除资产
     *
     *
     * @param userId
     * @param assetId
     * @return
     */
    Response deleteAsset(String userId, String assetId);

    List<AssetDTO> getAssetByName(String assetName, String userId);

    List<AssetDTO> getAssetByNameV2(String assetName, String userId);

    /**
     * 根据资产ID获取子资产列表
     *
     * @param assetId
     * @return
     */
    List<AssetDTO> getChildAssetListBy(String assetId);

    AssetDTO getTreeBy(String assetId, String userId);

    AssetDTO getTreeByV2(String assetId, String userId);

    AssetDTO getTreeWithoutDevice(String assetId, String userId);

    List<DeviceDTO> getChildDeviceInfoBy(String assetId);

    PageDataVO<DeviceDTO> getChildDeviceInfoPage(String assetId, Integer pageNo, Integer pageSize);

    void refreshTree();
}
