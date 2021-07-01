package com.tuya.iot.suite.web.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;

/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/29
 */
public class ShiroRedisCache implements Cache<Object,Object> {
    private String name;
    private RedisTemplate<String, Object> redisTemplate;
    public ShiroRedisCache(String name,RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.name = name;
        Assert.hasText(name,"必须指定缓存名称");
        Assert.isTrue(name.length()>4,"缓存名称不能少于5个字符");
    }
    private String generateStringKey(Object key){
        return name+":"+key.toString();
    }
    @Override
    public Object get(Object key) throws CacheException {
        return redisTemplate.opsForValue().get(generateStringKey(key));
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        return redisTemplate.opsForValue().getAndSet(generateStringKey(key), value);
    }

    @Override
    public Object remove(Object key) throws CacheException {
        String sk = generateStringKey(key);
        Object value = redisTemplate.opsForValue().get(sk);
        redisTemplate.delete(sk);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        Set<String> keys = redisTemplate.keys(generateStringKey("*"));
        redisTemplate.delete(keys);
    }

    @Override
    public int size() {
        Set<String> keys = redisTemplate.keys(generateStringKey("*"));
        return keys.size();
    }

    @Override
    public Set<Object> keys() {
        Set<?> keys = redisTemplate.keys(generateStringKey("*"));
        return (Set<Object>) keys;
    }

    @Override
    public Collection<Object> values() {
        Set<String> keys = redisTemplate.keys(generateStringKey("*"));
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public String getName() {
        return name;
    }

}