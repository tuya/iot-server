package com.tuya.iot.suite.web.model.criteria;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/12
 */
@Data
public class AssetCriteria implements Serializable {

    private static final long serialVersionUID = -1260872555499313451L;

    @ApiModelProperty(value = "父资产ID")
    private String parent_asset_id;

    @ApiModelProperty(value = "资产名称")
    private String asset_name;
}
