package com.tuya.iot.suite.service.dto;


import com.tuya.iot.suite.ability.asset.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * <p> TODO
 *
 * @author @author qiufeng.yu@tuya.com
 * @since 2021/4/20 11:32 下午
 */
@Mapper
public interface AssetConvertor {
    AssetConvertor $ = Mappers.getMapper(AssetConvertor.class);

    AssetVO toAssetVO(AssetDTO assetDTO);

    List<AssetVO> toAssetVOList(List<AssetDTO> assetDTOList);

    AssetDTO toAssetDTO(Asset asset);

    List<AssetDTO> toAssetDTOList(List<Asset> assetList);

}
