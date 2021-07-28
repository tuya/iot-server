package com.tuya.iot.server.ability.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/9
 */
@Getter
@Setter
@ToString
public class Asset implements Serializable {

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
     * 父资产id
     */
    private String parent_asset_id;

    /**
     * 设备ID
     */
    private String device_id;
    /**
     * 层级号
     */
    private Integer level;
}
