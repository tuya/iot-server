package com.tuya.iot.suite.core.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @since 2021/3/11
 */
public class SimpleConvertUtil {

    /**
     * TODO 先使用fastjson转换
     */
    public static <T> T convert(Object org, Class<T> clazz) {
        String jsonStr = JSONObject.toJSONString(org);
        return JSONObject.parseObject(jsonStr, clazz);
    }

    public static <T> List<T> convert(List<?> org, Class<T> clazz) {
        if (CollectionUtils.isEmpty(org)) {
            return new ArrayList<>();
        }
        List<T> dest = new ArrayList<T>();
        org.forEach(item -> dest.add(convert(item, clazz)));
        return dest;
    }
}
