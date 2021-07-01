package com.tuya.iot.suite.ability.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author mickey
 * @date 2021年06月02日 19:44
 */
@Getter
@Setter
@ToString
public class AssetAuthBatchToUser implements Serializable {

    private String uid;

    private String asset_ids;

    private Boolean authorized_children;

    public AssetAuthBatchToUser(String uid, String asset_ids, Boolean authorized_children) {
        this.uid = uid;
        this.asset_ids = asset_ids;
        this.authorized_children = authorized_children;
    }
}
