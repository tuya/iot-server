package com.tuya.iot.suite.ability.asset.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 已经授权的资产查询请求模型
 * @auther: Medivh.chen@tuya.com
 * @date: 2021/04/20
 **/
@Data
@AllArgsConstructor
public class AuthorizedAssetRequest implements Serializable {

    private static final long serialVersionUID = -2142911220040900193L;
    /**
     * 用户id
     */
    private String uid;

    /**
     * 页码
     */
    private int pageNo;
    /**
     * 每页大小
     */
    private int pageSize;


}
