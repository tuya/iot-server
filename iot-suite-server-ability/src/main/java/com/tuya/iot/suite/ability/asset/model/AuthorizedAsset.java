package com.tuya.iot.suite.ability.asset.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 已经授权的资产
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/20
 **/
@Data
@AllArgsConstructor
public class AuthorizedAsset implements Serializable {
    private static final long serialVersionUID = -3350891636365757991L;
    /**
     * 资产ID
     */
    private String assetId;

    /**
     * 父资产ID
     */
    private String parentAssetId;
    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 层级号
     */
    private Integer level;


}
