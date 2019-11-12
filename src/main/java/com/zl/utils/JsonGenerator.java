package com.zl.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**根据对象class生成json字符串,支持无限极嵌套，不支持循环引用，不支持初始化父类字段。
 * 解决问题:第三方json库不会将空集合或数组对象序列化成json字符串或者序列化成[],因为集合或
 *          数组是null或者是空集合、空数组。
 * 功能：初始化嵌套的自定义的javabean
 *      初始化嵌套的集合或数组并且添加一个对象进去
 *      初始化嵌套的日期类型
 * 调用支持：自定义javabean,如:User,调用generatorForObject方法
 *          自定义javabean的集合或数组,如:List<User>或User[],调用generatorForListOrArray方法
 * 自定义javabean中可嵌套的字段属性类型有：
 * 1.基本类型及其包装类
 * 2.基本类型及其包装类的集合或数组
 * 3.自定义javabean
 * 4.自定义javabean的集合或数组
 * 5.Date类型
 * 6.BigDecimal类型
 * @author zhangliang
 * @date 2019/11/11.
 */
@Slf4j
public class JsonGenerator {

    /**设置默认值
     * 目前支持:
     * 1.初始化集合且添加一个对象
     * 2.初始化数组且添加一个对象
     * 3.初始化日期
     * 4.初始化自定义javabean
     * @param c 对象class
     * @param o 对象
     * @throws Exception 反射新对象抛出异常
     */
    private static void setDefaultValue(Class c, Object o) throws Exception {
        Field[] declaredFields = c.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Class<?> type = declaredField.getType();
            if (needReflect(type)) {
                if (type.isAssignableFrom(List.class)) {
                    handleList(declaredField,o);
                } else if (type.isAssignableFrom(Date.class)) {
                    declaredField.set(o, new Date());
                } else if (type.isArray()) {
                    handleArray(declaredField,o,type);
                } else {
                    //自定义javabean
                    Object o1 = type.newInstance();
                    declaredField.set(o, o1);
                    setDefaultValue(type,o1);
                }
            }
        }
    }

    /**处理字段为集合的情况
     * @param declaredField 字段
     * @param o 字段所属对象
     * @throws Exception 反射新对象抛出异常
     */
    private static void handleList(Field declaredField,Object o) throws Exception{
        List<Object> dataList = new ArrayList<>();
        Type genericType = declaredField.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = ( ParameterizedType ) genericType;
            //得到泛型里的class类型对象
            Class<?> c2 = ( Class<?> ) pt.getActualTypeArguments()[0];
            Object generic = c2.newInstance();
            dataList.add(generic);
            declaredField.set(o, dataList);
            if (needReflect(c2)) {
                setDefaultValue(c2, generic);
            }
        }
    }

    /**处理字段为数组的情况
     * @param declaredField 字段
     * @param o 字段所属对象
     * @param c 字段类型
     * @throws Exception 反射新对象抛出异常
     */
    private static void handleArray(Field declaredField,Object o,Class c) throws Exception{
        Class<?> componentType = c.getComponentType();
        Object[] oArray = getObjectArray(componentType);
        Object o1 = componentType.newInstance();
        oArray[0] = o1;
        declaredField.set(o, oArray);
        if (needReflect(componentType)) {
            setDefaultValue(componentType, o1);
        }
    }

    /**根据class生成数组
     * @param c 对象class类型
     * @param <T> 数组类型
     * @return T[]
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] getObjectArray(Class<?> c) {
        return ( T[] ) Array.newInstance(c, 1);

    }

    /**是否需要反射 自定义的javabean需要,其它不需要
     * @param c 对象class类型
     * @return boolean trues 需要 ,false 不需要
     */
    private static boolean needReflect(Class c) {
        return !(c == BigDecimal.class || c == String.class || c.isPrimitive() || isBaseTypePackaging(c));
    }

    /**是否是基本类型的包装类
     * @param c 对象class类型
     * @return boolean trues 是 ,false 否
     */
    private static boolean isBaseTypePackaging(Class c) {
        return c.equals(java.lang.Integer.class) || c.equals(java.lang.Byte.class) || c.equals(java.lang.Long.class) || c.equals(java.lang.Double.class) || c.equals(java.lang.Float.class) || c.equals(java.lang.Character.class) || c.equals(java.lang.Short.class) || c.equals(java.lang.Boolean.class);
    }

    /**生成对象的json字符串
     * @param c 集合或数组的对象class类型
     * @return String
     */
    private static String generatorForObject(Class c) {
        try {
            Object o = c.newInstance();
            setDefaultValue(c, o);
            return JSONObject.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteMapNullValue, SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            log.error("generatorJson error:{0}", e);
        }
        return null;
    }

    /**生成对象集合或对象数组的json字符串
     * @param c 集合或数组的对象class类型
     * @return String
     */
    private static String generatorForListOrArray(Class c) {
        return "[" + generatorForObject(c) + "]";
    }
}
