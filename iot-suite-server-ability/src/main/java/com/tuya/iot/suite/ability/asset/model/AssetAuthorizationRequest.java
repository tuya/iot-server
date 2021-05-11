package com.tuya.iot.suite.ability.asset.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 资产授权请求数据模型
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetAuthorizationRequest implements Serializable {
    private static final long serialVersionUID = 8392308577501227408L;

    /**
     * 资产ID
     */
    private String uid;
    /**
     * 资产ID
     */
    private String asset_id;
    /**
     * 是否给子节点授权,默认是false
     */
    private boolean authorized_children;
}
