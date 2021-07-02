//package com.tuya.iot.suite.web.config.shiro;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheException;
//import org.apache.shiro.cache.CacheManager;
//
//import java.util.Map;
//
///**
// * @description:
// * @author: benguan.zhou@tuya.com
// * @date: 2021/05/29
// */
//public class ShiroRedisCacheManager implements CacheManager {
//
//    @Getter
//    @Setter
//    private Map<String,ShiroRedisCache> cacheMap;
//    @Override
//    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
//        return (Cache<K, V>) cacheMap.get(name);
//    }
//
//}
//
