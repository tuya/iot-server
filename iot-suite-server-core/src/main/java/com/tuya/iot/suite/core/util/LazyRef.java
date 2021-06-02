package com.tuya.iot.suite.core.util;

import java.util.function.Supplier;

/**
 * 该工具可以解决启动时服务依赖问题。
 * 延迟执行，还可以加快系统的启动速度。
 * 比如有些模块的组件在启动时调用一些服务，查询数据缓存起来。
 * 后面再用的时候都不用再调用服务。
 * 这样虽然避免了多次调用服务，减少了资源占用，提升了性能， 但是增加了耦合性（启动时的时序耦合）。
 * 使用该工具后的效果，可以保证一些操作最多只执行一次。
 * 并且不用在启动时执行。不过这要对代码稍微调整一下。
 * 可以作为spring的lazy-init的协作工具。使一部分代码即时执行，另一部分代码延迟执行。
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/06/02
 */
public class LazyRef<T> {
    private volatile T value;
    private final Supplier<T> supplier;
    protected LazyRef(Supplier<T> supplier){
        if(supplier==null){
            throw new RuntimeException("supplier cannot be null!");
        }
        this.supplier = supplier;
    }
    public T get(){
        if(value==null){
            synchronized (this){
                if(value==null){
                    value = supplier.get();
                    if(value==null){
                        throw new RuntimeException("result of supplier.get() cannot be null!");
                    }
                }
            }
        }
        return value;
    }
    public static <T> LazyRef<T> lateInit(Supplier<T> supplier){
        return new LazyRef<>(supplier);
    }
}