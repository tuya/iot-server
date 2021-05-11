package com.tuya.iot.suite.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/9
 */
@Data
public class AssetVO implements Serializable {

    private static final long serialVersionUID = 3658227130368171924L;

    /**
     * 资产Id
     */
    private String asset_id;

    /**
     * 资产名
     */
    private String asset_name;

    /**
     * 资产全名
     */
    private String asset_full_name;

    /**
     * 子资产数量
     */
    private Integer child_asset_count;

    /**
     * 子设备数量
     */
    private Integer child_device_count;

    /**
     * 子资产
     */
    private List<AssetVO> subAssets;
}
