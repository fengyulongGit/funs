package com.autils.api.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jisuyun on 2017/8/29.
 */

public class ObjectUtils {

    public static Map<String, Object> mapProperties(Object bean) {
        Map<String, Object> properties = new HashMap<>();
        try {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())
                        && method.getParameterTypes().length == 0
                        && method.getReturnType() != void.class
                        && method.getName().matches("^(get|is).+")
                ) {
                    String name = method.getName().replaceAll("^(get|is)", "");
                    name = Character.toLowerCase(name.charAt(0)) + (name.length() > 1 ? name.substring(1) : "");
                    Object value = method.invoke(bean);
                    if (value == null) {
                        value = "";
                    }
                    properties.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

}
