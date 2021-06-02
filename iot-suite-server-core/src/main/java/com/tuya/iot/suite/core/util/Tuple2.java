package com.tuya.iot.suite.core.util;

/**
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/02
 */
public class Tuple2<V1,V2>{
    private V1 v1;
    private V2 v2;
    public Tuple2(V1 v1,V2 v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    public V1 first(){
        return v1;
    }
    public V2 second(){
        return v2;
    }
}
