package com.dental.home.app.utils;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author junking
 */
public class ResultMap {

    /**
     * 生成resultMap
     * @param key
     * @param value
     * @return
     */
    public static Map<String, Object> resultMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(key, value);
        return map;
    }


    /**
     * 把对象转为map
     * @param obj
     * @param keepNullVal 保留空值
     * @return
     */
    public static Map objectToMap(Object obj, boolean keepNullVal) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal == true) {
                    map.put(field.getName(), field.get(obj));
                } else {
                    if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
                        map.put(field.getName(), field.get(obj));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
