package com.tuya.iot.suite.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @auth:mickey
 * @createTime 2021-05-26
 */
public class JacksonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 忽略空Bean转Json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有日期格式统一为 yyyy:MM:dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略在Json字符串中存在，但在Java对象中不存在的对应属性的状况，防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 方法功能描述 :
     *
     * @param t
     * @return
     * @author xiaojun.yin
     * 2018年11月8日 上午11:42:40
     */
    public static <T> String ObjectToJsonString(T t) throws JsonProcessingException {
        return objectMapper.writeValueAsString(t);
    }

    /**
     * 方法功能描述 :接送中读取list
     *
     * @param json
     * @param clazz
     * @return
     * @author xiaojun.yin
     * 2018年11月8日 上午11:47:37
     */
    public static <T> List<T> readListValue(String json, Class<T> clazz) throws JsonProcessingException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        return objectMapper.readValue(json, javaType);
    }

    /**
     * 方法功能描述 :json中读取obj
     *
     * @param json
     * @param clazz
     * @return
     * @author xiaojun.yin
     * 2018年11月8日 上午11:54:46
     */
    public static <T> T readObjectValue(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 方法功能描述 :对象转成map
     *
     * @param t
     * @return
     * @throws IllegalAccessException
     * @author xiaojun.yin
     * 2018年11月8日 下午1:28:12
     */
    @SuppressWarnings("rawtypes")
    public static <T> Map<String, Object> objectToMap(T t) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class clazz = t.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(t);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 方法功能描述 :map转Object
     *
     * @param objMap
     * @param clazz
     * @return
     * @author xiaojun.yin
     * 2018年11月8日 下午1:30:54
     */
    public static <T> T MapToObject(Map<String, Object> objMap, Class<T> clazz) throws JsonProcessingException {
        String objJson = ObjectToJsonString(objMap);
        return readObjectValue(objJson, clazz);
    }


    public static <T> List<T> readvalue(String fieldName, String zhongJiStr, Class<T> clazz) throws JsonProcessingException {
        JsonNode jn1 = objectMapper.readTree(zhongJiStr);
        List<JsonNode> jns = jn1.findValues(fieldName);
        if (jns.isEmpty()) {
            return null;
        }
        return readListValue(JacksonUtils.ObjectToJsonString(jns.get(0)), clazz);
    }
}
