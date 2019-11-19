package com.thinkcore.preference;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Properties;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Android的Properties类型配置文件操作类
 */
public class TPropertiesConfig implements IConfig {
    private static final String LOADFLAG = "assetsload";

    /**
     * assets中配置信息文件
     */
    private String assetsPath = "/assets/config.properties";
    /**
     * 软件Files文件夹中配置信息文件
     */
    private String fileName = "config.properties";
    private static TPropertiesConfig that;

    private Context context;
    private Properties properties;

    public TPropertiesConfig(Context context) {
        this.context = context;
    }

//    private void loadConfig() {
//        Properties props = new Properties();
//        InputStream in = TPropertiesConfig.class
//                .getResourceAsStream(assetsPath);
//        try {
//            if (in != null) {
//                props.load(in);
//                Enumeration<?> e = props.propertyNames();
//                if (e.hasMoreElements()) {
//                    while (e.hasMoreElements()) {
//                        String s = (String) e.nextElement();
//                        props.setProperty(s, props.getProperty(s));
//                    }
//                }
//            }
//            setBoolean(LOADFLAG, true);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null)
//                    in.close();
//            } catch (IOException e) {
//            }
//        }
//    }

    @Override
    public void open(String name) {
        fileName = name + ".properties";

        Properties props = new Properties();
        try {
            InputStream in = context.openFileInput(fileName);
            props.load(in);
            in.close();
        } catch (IOException e) {
            Log.e("Properties", e.getMessage());
        }
        properties = props;
    }


    @Override
    public void close() {
        fileName = "";
        properties = null;
    }


    @Override
    public Boolean isLoadConfig() {
        return properties != null;
    }

    public void setConfig(String key, String value) {
        if (value != null) {
            properties.setProperty(key, value);
            storeProperties();
        }
    }

    public String getAssetsPath() {
        return assetsPath;
    }

    public void setAssetsPath(String assetsPath) {
        this.assetsPath = assetsPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void storeProperties() {
        OutputStream out = null;
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            properties.store(out, null);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
            }
        }
    }


    @Override
    public void setString(String key, String value) {
        setConfig(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        setString(key, String.valueOf(value));
    }

    @Override
    public void setBoolean(String key, Boolean value) {

        setString(key, String.valueOf(value));
    }

    @Override
    public void setByte(String key, byte[] value) {

        setString(key, String.valueOf(value));
    }

    @Override
    public void setShort(String key, short value) {

        setString(key, String.valueOf(value));
    }

    @Override
    public void setLong(String key, long value) {

        setString(key, String.valueOf(value));
    }

    @Override
    public void setFloat(String key, float value) {

        setString(key, String.valueOf(value));
    }

    @Override
    public void setDouble(String key, double value) {

        setString(key, String.valueOf(value));
    }

    public String getConfig(String key, String defaultValue) {

        return properties.getProperty(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {

        return getConfig(key, defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {

        try {
            return Integer.valueOf(getString(key, ""));
        } catch (Exception e) {

        }
        return defaultValue;

    }

    @Override
    public boolean getBoolean(String key, Boolean defaultValue) {

        try {
            return Boolean.valueOf(getString(key, ""));
        } catch (Exception e) {

        }
        return defaultValue;
    }

    @Override
    public byte[] getByte(String key, byte[] defaultValue) {

        try {
            return getString(key, "").getBytes();
        } catch (Exception e) {

        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, Short defaultValue) {

        try {
            return Short.valueOf(getString(key, ""));
        } catch (Exception e) {

        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, Long defaultValue) {

        try {
            return Long.valueOf(getString(key, ""));
        } catch (Exception e) {

        }
        return defaultValue;
    }

    @Override
    public float getFloat(String key, Float defaultValue) {

        try {
            return Float.valueOf(getString(key, ""));
        } catch (Exception e) {

        }
        return defaultValue;
    }

    @Override
    public double getDouble(String key, Double defaultValue) {

        try {
            return Double.valueOf(getString(key, ""));
        } catch (Exception e) {
        }
        return defaultValue;
    }

    @Override
    public String getString(int resID, String defaultValue) {
        return getString(this.context.getString(resID), defaultValue);
    }

    @Override
    public int getInt(int resID, int defaultValue) {

        return getInt(this.context.getString(resID), defaultValue);
    }

    @Override
    public boolean getBoolean(int resID, Boolean defaultValue) {

        return getBoolean(this.context.getString(resID), defaultValue);
    }

    @Override
    public byte[] getByte(int resID, byte[] defaultValue) {

        return getByte(this.context.getString(resID), defaultValue);
    }

    @Override
    public short getShort(int resID, Short defaultValue) {

        return getShort(this.context.getString(resID), defaultValue);
    }

    @Override
    public long getLong(int resID, Long defaultValue) {

        return getLong(this.context.getString(resID), defaultValue);
    }

    @Override
    public float getFloat(int resID, Float defaultValue) {

        return getFloat(this.context.getString(resID), defaultValue);
    }

    @Override
    public double getDouble(int resID, Double defaultValue) {

        return getDouble(this.context.getString(resID), defaultValue);
    }

    @Override
    public void setString(int resID, String value) {

        setString(this.context.getString(resID), value);
    }

    @Override
    public void setInt(int resID, int value) {

        setInt(this.context.getString(resID), value);
    }

    @Override
    public void setBoolean(int resID, Boolean value) {

        setBoolean(this.context.getString(resID), value);
    }

    @Override
    public void setByte(int resID, byte[] value) {

        setByte(this.context.getString(resID), value);
    }

    @Override
    public void setShort(int resID, short value) {

        setShort(this.context.getString(resID), value);
    }

    @Override
    public void setLong(int resID, long value) {

        setLong(this.context.getString(resID), value);
    }

    @Override
    public void setFloat(int resID, float value) {

        setFloat(this.context.getString(resID), value);
    }

    @Override
    public void setDouble(int resID, double value) {

        setDouble(this.context.getString(resID), value);
    }

    @Override
    public void setConfig(Object entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            if (!Utils.isTransient(field)) {
                if (Utils.isBaseDateType(field)) {
                    String columnName = Utils.getFieldName(field);
                    field.setAccessible(true);
                    setValue(field, columnName, entity);
                }
            }
        }
    }

    private void setValue(Field field, String columnName, Object entity) {
        try {
            Class<?> clazz = field.getType();
            if (clazz.equals(String.class)) {
                setString(columnName, (String) field.get(entity));
            } else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                setInt(columnName, (Integer) field.get(entity));
            } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
                setFloat(columnName, (Float) field.get(entity));
            } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
                setDouble(columnName, (Double) field.get(entity));
            } else if (clazz.equals(Short.class) || clazz.equals(Short.class)) {
                setShort(columnName, (Short) field.get(entity));
            } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
                setLong(columnName, (Long) field.get(entity));
            } else if (clazz.equals(Boolean.class)) {
                setBoolean(columnName, (Boolean) field.get(entity));
            }
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }

    }

    @Override
    public <T> T getConfig(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        T entity = null;
        try {
            entity = (T) clazz.newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Utils.isTransient(field)) {
                    if (Utils.isBaseDateType(field)) {

                        String columnName = Utils.getFieldName(field);
                        field.setAccessible(true);
                        getValue(field, columnName, entity);
                    }
                }

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private <T> void getValue(Field field, String columnName, T entity) {
        try {
            Class<?> clazz = field.getType();
            if (clazz.equals(String.class)) {
                field.set(entity, getString(columnName, ""));
            } else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                field.set(entity, getInt(columnName, 0));
            } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
                field.set(entity, getFloat(columnName, 0f));
            } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
                field.set(entity, getDouble(columnName, 0.0));
            } else if (clazz.equals(Short.class) || clazz.equals(Short.class)) {
                field.set(entity, getShort(columnName, (short) 0));
            } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
                field.set(entity, getLong(columnName, 0l));
            } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
                field.set(entity, getByte(columnName, new byte[8]));
            } else if (clazz.equals(Boolean.class)) {
                field.set(entity, getBoolean(columnName, false));
            }
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void remove(String key) {
        properties.remove(key);
        storeProperties();
    }

    @Override
    public void remove(String... keys) {
        for (String key : keys) {
            properties.remove(key);
        }
        storeProperties();
    }

    @Override
    public void removeAll() {
        clear();
    }

    @Override
    public void clear() {
        properties.clear();
        storeProperties();
    }


    ////////////////////////////////////////////////////////

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
