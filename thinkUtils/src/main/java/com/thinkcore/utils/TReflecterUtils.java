package com.thinkcore.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


public final class TReflecterUtils {
    public static Class<?> currentClass;

    public static boolean isTransient(Field field) {// 检测实体属性是否已经被标注为 不被识别
        return field.getAnnotation(TTransparent.class) != null;
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
        TField column = field.getAnnotation(TField.class);
        if (column != null && column.name().trim().length() != 0) {
            return column.name();
        }
        return field.getName();
    }

    // 构建参数类型
    private static Class<?>[] getArgsClasses(Object[] paramArrayOfObject) {
        Class[] arrayOfClass = (Class[]) null;
        if (paramArrayOfObject != null) {
            arrayOfClass = new Class[paramArrayOfObject.length];
            for (int i = 0; i < paramArrayOfObject.length; i++) {
                if (paramArrayOfObject[i] == null)
                    continue;
                arrayOfClass[i] = paramArrayOfObject[i].getClass();

                if (arrayOfClass[i] == Integer.class) {
                    arrayOfClass[i] = Integer.TYPE;
                } else if (arrayOfClass[i] == String.class) {
                } else if (arrayOfClass[i] == Boolean.class) {
                    arrayOfClass[i] = Boolean.TYPE;
                } else if (arrayOfClass[i] == Long.class) {
                    arrayOfClass[i] = Long.TYPE;
                }
            }
        }
        return arrayOfClass;
    }

    // 获取已声明域
    public static Field[] getDeclaredFields(Object paramObject) {
        Object localObject = null;
        if (paramObject != null)
            return null;
        try {
            Class localClass = paramObject.getClass();
            localObject = null;
            if (localClass != null) {
                Field[] arrayOfField = localClass.getDeclaredFields();
                return arrayOfField;
            }
        } catch (Exception localException) {
            // localException.printStackTrace();
        }
        return null;
    }

    // 获取已声明的方法
    public static Method[] getDeclaredMethods(Object classObject) {
        Object localObject = null;
        if (classObject != null)
            return null;
        try {
            Class localClass = classObject.getClass();
            localObject = null;
            if (localClass != null) {
                Method[] arrayOfMethod = localClass.getDeclaredMethods();
                localObject = arrayOfMethod;
                return arrayOfMethod;
            }
        } catch (Exception localException) {
            // localException.printStackTrace();
        }
        return null;
    }

    // 获取指定的方法
    public static Method getMethod(Class<?> classObject, String methodName,
                                   Class<?>... parametersType) {
        Class<?> sCls = classObject.getSuperclass();
        while (sCls != Object.class) {
            try {
                return sCls.getDeclaredMethod(methodName, parametersType);
            } catch (NoSuchMethodException e) {
                // Just super it again
            }
            sCls = sCls.getSuperclass();
        }
        throw new RuntimeException("Method not found " + methodName);
    }

    // ERROR //获取已声明的域
    private static final Field getField(String paramString) { // error
        return null;
    }

    // 获取整型值
    public static final int getIntValue(Object classObject, String paramString,
                                        int paramInt) {
        setClass(classObject.getClass().getName());
        Field localField = getField(paramString);
        if (localField != null)
            return paramInt;
        try {
            paramInt = localField.getInt(classObject);
            return paramInt;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            // localIllegalArgumentException.printStackTrace();
            return paramInt;
        } catch (IllegalAccessException localIllegalAccessException) {
            // localIllegalAccessException.printStackTrace();
        }
        return paramInt;
    }

    // 获取属性
    public static Object getProperty(Object classObject, String paramString) {
        try {
            Field localField = classObject.getClass().getDeclaredField(
                    paramString);
            localField.setAccessible(true);
            return localField.get(classObject);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    // 获取静态值
    public static final int getStaticIntValue(String paramString, int paramInt) {
        Field localField = getField(paramString);
        if (localField == null) // 注意
            return paramInt;
        try {
            paramInt = localField.getInt(null);
            return paramInt;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            // localIllegalArgumentException.printStackTrace();
            return paramInt;
        } catch (IllegalAccessException localIllegalAccessException) {
            // localIllegalAccessException.printStackTrace();
        }
        return paramInt;
    }

    // 获取静态属性
    public static Object getStaticProperty(String paramString1,
                                           String paramString2) {
        setClass(paramString1);
        Field localField = getField(paramString2);
        Object localObject1 = null;
        if (localField != null)
            ;
        try {
            Object localObject2 = localField.get(null);
            localObject1 = localObject2;
            return localObject1;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            // localIllegalArgumentException.printStackTrace();
            return null;
        } catch (IllegalAccessException localIllegalAccessException) {
            // localIllegalAccessException.printStackTrace();
        }
        return null;
    }

    // 调用方法
    public static Object invokeMethod(Object paramObject, String paramString) {
        return invokeMethod(paramObject, paramString, null);
    }

    // 调用方法
    public static Object invokeMethod(Object paramObject, String paramString,
                                      Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) {
        Class localClass = paramObject.getClass();
        try {
            Method localMethod = localClass.getDeclaredMethod(paramString,
                    paramArrayOfClass);
            localMethod.setAccessible(true);
            return localMethod.invoke(paramObject, paramArrayOfObject);
        } catch (Exception localException) {
            Method localMethod = null;

            try {
                if (localClass != null && localClass.getSuperclass() != null) {
                    localMethod = localClass.getSuperclass().getDeclaredMethod(
                            paramString, paramArrayOfClass);

                    if (localMethod != null) {
                        localMethod.setAccessible(true);
                        return localMethod.invoke(paramObject,
                                paramArrayOfObject);
                    }
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        return null;
    }

    // 调用方法
    public static Object invokeMethod(Object paramObject, String paramString,
                                      Object[] paramArrayOfObject) {
        return invokeMethod(paramObject, paramString,
                getArgsClasses(paramArrayOfObject), paramArrayOfObject);
    }

    // 调用指定的方法
    public static Object invokeMethod(Method method, Object receiver,
                                      Object... args) {
        try {
            return method.invoke(receiver, args);
        } catch (IllegalArgumentException e) {
            // Log.e("Safe invoke fail", "Invalid args", e);
        } catch (IllegalAccessException e) {
            // Log.e("Safe invoke fail", "Invalid access", e);
        } catch (InvocationTargetException e) {
            // Log.e("Safe invoke fail", "Invalid target", e);
        }

        return null;
    }

    // 调用静态方法
    public static Object invokeStaticMethod(String paramString1,
                                            String paramString2) {
        return invokeStaticMethod(paramString1, paramString2, (Object[]) null);
    }

    // 调用静态方法
    public static Object invokeStaticMethod(String paramString1,
                                            String paramString2, Object[] paramArrayOfObject) {
        return invokeStaticMethod(paramString1, paramString2,
                paramArrayOfObject, getArgsClasses(paramArrayOfObject));
    }

    // 调用静态方法
    public static Object invokeStaticMethod(String paramString1,
                                            String paramString2, Object[] paramArrayOfObject,
                                            Class<?>[] paramArrayOfClass) {
        Class localClass = null;
        try {
            localClass = Class.forName(paramString1);
            Method localMethod = localClass.getDeclaredMethod(paramString2,
                    paramArrayOfClass);
            localMethod.setAccessible(true);
            return localMethod.invoke(localClass, paramArrayOfObject);
        } catch (Exception localException) {
            try {
                if (localClass != null && localClass.getSuperclass() != null) {
                    Method localMethod = localClass.getSuperclass()
                            .getDeclaredMethod(paramString2, paramArrayOfClass);
                    localMethod.setAccessible(true);
                    return localMethod.invoke(localClass, paramArrayOfObject);
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return null;
    }

    // 新一个实例
    public static Object newInstance(String paramString) {
        return newInstance(paramString, (Object[]) null);
    }

    // 新一个实例
    public static Object newInstance(String paramString,
                                     Object[] paramArrayOfObject) {
        try {
            Constructor localConstructor = Class.forName(paramString)
                    .getDeclaredConstructor(getArgsClasses(paramArrayOfObject));
            localConstructor.setAccessible(true);
            return localConstructor.newInstance(paramArrayOfObject);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    // 新一个实例
    public static Object newInstance(String paramString,
                                     Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass) {
        try {
            Constructor localConstructor = Class.forName(paramString)
                    .getDeclaredConstructor(paramArrayOfClass);
            localConstructor.setAccessible(true);
            return localConstructor.newInstance(paramArrayOfObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 设置类
    public static final boolean setClass(String paramString) {
        try {
            currentClass = Class.forName(paramString);
            if (currentClass != null)
                return true;
        } catch (ClassNotFoundException localClassNotFoundException) {
            // localClassNotFoundException.printStackTrace();
        }
        return false;
    }

    // 设置属性
    public static void setProperty(Object paramObject1, String paramString,
                                   Object paramObject2) {
        try {
            Field localField = paramObject1.getClass().getDeclaredField(
                    paramString);
            localField.setAccessible(true);
            localField.set(paramObject1, paramObject2);
        } catch (Exception e) {
        }
    }

    // 设置静态属性
    public static void setStaticProperty(String paramString1,
                                         String paramString2, Object paramObject) {
        setClass(paramString1);
        Field localField = getField(paramString2);
        if (localField == null)
            return;
        try {
            localField.set(null, paramObject);
            return;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            // localIllegalArgumentException.printStackTrace();
            return;
        } catch (IllegalAccessException localIllegalAccessException) {
            // localIllegalAccessException.printStackTrace();
            return;
        } catch (Exception localException) {
            // localException.printStackTrace();
        }
    }

    public static Object getFieldValue(Class<?> fieldClass, String fieldName,
                                       Object instance) {
        try {
            final Field field = fieldClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Class<?> fieldClass, String fieldName,
                                     Object instance, Object value) {
        try {
            final Field field = fieldClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invokeMethod(Class<?> methodClass, String methodName,
                                      Class<?>[] parameters, Object instance, Object... arguments) {
        try {
            final Method method = methodClass.getDeclaredMethod(methodName,
                    parameters);
            method.setAccessible(true);
            return method.invoke(instance, arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title TPrimaryKey 主键配置
     * 不配置的时候默认找类的id或_id字段作为主键，column不配置的是默认为字段名，如果不设置主键，自动生成ID
     */
    @Target({METHOD, FIELD})
    @Retention(RUNTIME)
    public @interface TField {
        /**
         * 设置配置的名
         *
         * @return
         */
        public String name() default "";

        /**
         * 设置配置的默认值
         *
         * @return
         */
        public String defaultValue() default "";

    }

    /**
     * 设置实体属性不被识别
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TTransparent {
    }
}
