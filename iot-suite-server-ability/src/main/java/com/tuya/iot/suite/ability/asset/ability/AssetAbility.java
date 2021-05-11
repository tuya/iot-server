package com.tuya.iot.suite.ability.asset.ability;


import com.tuya.connector.open.api.model.PageResult;
import com.tuya.iot.suite.ability.asset.model.*;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/11
 */
public interface AssetAbility {

    String addAsset(AssetAddRequest request);

    Boolean modifyAsset(String assetId, AssetModifyRequest body);

    Boolean deleteAsset(String assetId);

    /**
     * 查询空间节点
     * 根据id查询资产
     *
     * @param assetId 资产id
     * @return
     */
    Asset selectAsset(String assetId);

    /**
     * 查询空间节点
     * 根据id查询资产
     *
     * @param assetIds 资产id
     * @return
     */
    List<Asset> selectAssets(String assetIds);


    /**
     * 批量查询空间节点
     * 根据id列表批量查询 最大100
     *
     * @param assetIds   资产id列表
     * @param assetName
     * @param lastRowKey
     * @param pageSize
     * @return
     */
    PageResult<Asset> selectAssets(String assetIds, String assetName, String lastRowKey, Integer pageSize);

    /**
     * 当前节点下子节点分页查询
     * 分页查询子节点资产
     *
     * @param assetId    资产id
     * @param lastRowKey 资产最后一条记录的资产id
     * @param pageSize   页面大小
     * @return
     */
    PageResult<Asset> selectChildAssets(String assetId, String lastRowKey, String pageSize);

    PageResult<Asset> selectChildDevices(String assetId, String lastRowKey, String pageSize);

    Boolean authorized(AssetAuthorizationRequest assetAuthorizationRequest);

    PageResultWithTotal<AuthorizedAsset> pageListAuthorizedAssets(String uid, int pageNo, int pageSize);
}
