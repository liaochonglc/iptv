package com.ido.iptv.utils;

import com.ido.iptv.exception.BeanException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * javabean -> map 工具类
 *
 * @author: wujun
 * @date: 2018/7/24 12:02
 */
public class BeanUtils {

    /**
     * 将javabean 转换为 map
     *
     * @param target 待转换对象
     * @return
     */
    public static <T> Map<String, Object> bean2map(T target) {

        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (Objects.equals("class", propertyDescriptor.getName())) {
                    continue;
                }

                String key = propertyDescriptor.getName();
                Object value = propertyDescriptor.getReadMethod().invoke(target);

                if (value instanceof String && StringUtils.hasText((String) value)) {
                    map.put(key, value);
                } else if (value != null) {
                    map.put(key, value);
                }
            }
        } catch (IntrospectionException |
                IllegalAccessException |
                InvocationTargetException e) {
            throw new BeanException(e.getMessage(), e);
        }

        return map;
    }

    /**
     * map 转 javabean
     *
     * @param map
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T map2bean(Map<String, Object> map, Class<T> T) {
        Assert.notEmpty(map, "待转换map不能为空");

        try {
            T t = T.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(T);
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if (Objects.equals("class", propertyDescriptor.getName())) {
                    continue;
                }

                String key = propertyDescriptor.getName();

                if (map.containsKey(key)) {
                    propertyDescriptor.getWriteMethod().invoke(t, map.get(key));
                }
            }

            return t;
        } catch (IntrospectionException |
                IllegalAccessException |
                InstantiationException |
                InvocationTargetException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }
}
