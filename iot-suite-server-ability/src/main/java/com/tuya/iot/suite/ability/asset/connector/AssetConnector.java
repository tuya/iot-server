package com.tuya.iot.suite.ability.asset.connector;


import com.tuya.connector.api.annotations.*;
import com.tuya.connector.open.api.model.PageResult;
import com.tuya.iot.suite.ability.asset.ability.AssetAbility;
import com.tuya.iot.suite.ability.asset.model.*;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/10
 */
public interface AssetConnector extends AssetAbility {

    @Override
    @POST("/v1.0/iot-02/assets")
    String addAsset(@Body AssetAddRequest request);

    @Override
    @PUT("/v1.0/iot-02/assets/{asset_id}")
    Boolean modifyAsset(@Path("asset_id") String assetId, @Body AssetModifyRequest body);

    @Override
    @DELETE("/v1.0/iot-02/assets/{asset_id}")
    Boolean deleteAsset(@Path("asset_id") String assetId);

    @Override
    @GET("/v1.0/iot-02/assets/{asset_id}")
    Asset selectAsset(@Path("asset_id") String assetId);

    @Override
    @GET("/v1.0/iot-02/assets")
    List<Asset> selectAssets(@Query("asset_ids") String assetIds);

    @Override
    @GET("/v1.1/iot-02/assets")
    PageResult<Asset> selectAssets(@Query("asset_ids") String assetIds, @Query("asset_name") String assetName,
                                   @Query("last_row_key") String lastRowKey, @Query("page_size") Integer pageSize);

    @Override
    @GET("/v1.0/iot-02/assets/{asset_id}/sub-assets")
    PageResult<Asset> selectChildAssets(@Path("asset_id") String assetId, @Query("last_row_key") String lastRowKey,
                                        @Query("page_size") String pageSize);

    @Override
    @GET("/v1.0/iot-02/assets/{asset_id}/devices")
    PageResult<Asset> selectChildDevices(@Path("asset_id") String assetId, @Query("last_row_key") String lastRowKey,
                                         @Query("page_size") String pageSize);

    @Override
    @POST("/v1.0/iot-03/assets/actions/user-authorized")
    Boolean authorized(@Body AssetAuthorizationRequest assetAuthorizationRequest);

    @Override
    @POST("/v1.0/iot-03/users/{uid}/actions/batch-assets-authorized")
    Boolean batchAssetsAuthorizedToUser(@Path("uid") String userId, @Body AssetAuthToUser assetAuthToUser);


    @Override
    @GET("/v1.0/iot-03/users/{uid}/assets")
    PageResultWithTotal<AuthorizedAsset> pageListAuthorizedAssets(@Path("uid") String assetId,
                                                                  @Query("pageNo") int pageNo,
                                                                  @Query("pageSize") int pageSize);

}
