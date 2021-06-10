package com.tuya.iot.suite.model;

import com.tuya.iot.suite.service.util.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/10
 */
@Data
public class Area implements TreeNode<String,Area> {
    String code;
    String parentCode;
    List<Area> children;
    @Override
    public String code() {
        return code;
    }

    @Override
    public void code(String code) {
        this.code = code;
    }

    @Override
    public String parentCode() {
        return parentCode;
    }

    @Override
    public void parentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public List<Area> children() {
        return children;
    }

    @Override
    public void children(List<Area> children) {
        this.children = children;
    }
}
