package com.tuya.iot.suite.service.util;

import java.util.List;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/09
 */
public interface TreeNode<T, E extends TreeNode<T, E>> {
    T code();

    void code(T code);

    T parentCode();

    void parentCode(T parentCode);

    List<E> children();

    void children(List<E> children);
}
