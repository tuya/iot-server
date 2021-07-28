package com.tuya.iot.server.web.model.request.asset;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author mickey
 * @date 2021年06月01日 16:27
 */
@Getter
@Setter
@ToString
public class AssetAuths implements Serializable {

    @ApiModelProperty("用户id")
    private  String userId;
    @ApiModelProperty("资产id集合")
    private List<String> assetIds;

}
