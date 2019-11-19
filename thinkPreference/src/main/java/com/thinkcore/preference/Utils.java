package com.thinkcore.preference;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by banketree on 2017/8/8.
 */

public class Utils {
    public static boolean isTransient(Field field) {// 检测实体属性是否已经被标注为 不被识别
        return field.getAnnotation(TPropertiesConfig.TTransparent.class) != null;
    }

    public static boolean isBaseDateType(Field field) {// 是否为基本的数据类型
        Class<?> clazz = field.getType();
        return clazz.equals(String.class) || clazz.equals(Integer.class)
                || clazz.equals(Byte.class) || clazz.equals(Long.class)
                || clazz.equals(Double.class) || clazz.equals(Float.class)
                || clazz.equals(Character.class) || clazz.equals(Short.class)
                || clazz.equals(Boolean.class) || clazz.equals(Date.class)
                || clazz.equals(Date.class)
                || clazz.equals(java.sql.Date.class) || clazz.isPrimitive();
    }

    public static String getFieldName(Field field) {// 获得配置名
        TPropertiesConfig.TField column = field.getAnnotation(TPropertiesConfig.TField.class);
        if (column != null && column.name().trim().length() != 0) {
            return column.name();
        }
        return field.getName();
    }
}
