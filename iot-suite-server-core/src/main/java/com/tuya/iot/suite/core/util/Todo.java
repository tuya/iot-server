package com.tuya.iot.suite.core.util;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/27
 */
public interface Todo {
    static <T> T todo(){
        throw new RuntimeException("this method has not implemented!");
    }
    static <T> T todo(String function){
        throw new RuntimeException(function+" has not implemented!");
    }
}
