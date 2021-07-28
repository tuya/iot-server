package com.tuya.iot.server.ability.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mickey
 * @date 2021年06月02日 19:44
 */
@Getter
@Setter
@ToString
public class AssetAuthToUser implements Serializable {

    private String uid;

    private String asset_id;

    private Boolean authorized_children;

    public AssetAuthToUser(String uid, String asset_id, Boolean authorized_children) {
        this.uid = uid;
        this.asset_id = asset_id;
        this.authorized_children = authorized_children;
    }
}
