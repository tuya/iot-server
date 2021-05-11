package com.tuya.iot.suite.ability.asset.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/27
 */
@Data
public class AssetAddRequest implements Serializable {

    private static final long serialVersionUID = -4413618452512443438L;
    private String name;
    private String meta_id;
    private String parent_asset_id;
}
