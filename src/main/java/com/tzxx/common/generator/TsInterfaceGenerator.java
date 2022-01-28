package com.tzxx.common.generator;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhangliang
 * @date 2021/5/11.
 */
@Slf4j
public class TsInterfaceGenerator {
    private TsInterfaceGenerator(){}
    private static final Map<Class<?>, String> MAP = new HashMap<>();
    private static final Set<Class<?>> CREATING_CLASS_SET = new HashSet<>();
    private static final Set<StringBuilder> BUILDERS = new HashSet<>();
    private static final String NUMBER_TYPE = "number";

    static {
        MAP.put(int.class, NUMBER_TYPE);
        MAP.put(double.class, NUMBER_TYPE);
        MAP.put(short.class, NUMBER_TYPE);
        MAP.put(long.class, NUMBER_TYPE);
        MAP.put(float.class, NUMBER_TYPE);
        MAP.put(boolean.class, "boolean");
        MAP.put(Integer.class, NUMBER_TYPE);
        MAP.put(Double.class, NUMBER_TYPE);
        MAP.put(Short.class, NUMBER_TYPE);
        MAP.put(Long.class, NUMBER_TYPE);
        MAP.put(Float.class, NUMBER_TYPE);
        MAP.put(Boolean.class, "boolean");
        MAP.put(BigDecimal.class, NUMBER_TYPE);
        MAP.put(Date.class, "Date");
        MAP.put(String.class, "string");
    }

    private static void normalTypeAppend(StringBuilder builder, Field declaredField, Class<?> type, boolean array) {
        builder.append("  ");
        builder.append(declaredField.getName());
        builder.append(": ");
        builder.append(MAP.get(type));
        if (array) {
            builder.append("[]");
        }
        builder.append(";");
        builder.append("\r\n");
    }

    private static void javaBeanAppend(StringBuilder builder, Field declaredField, Class<?> type, boolean array, String... deleteSuffix) {
        builder.append("  ");
        builder.append(declaredField.getName());
        builder.append(": ");
        builder.append(deleteSuffix(type, deleteSuffix));
        if (array) {
            builder.append("[]");
        }
        builder.append(";");
        builder.append("\r\n");
    }

    private static String deleteSuffix(Class<?> c, String... deleteSuffix) {
        if (deleteSuffix != null && deleteSuffix.length > 0) {
            String simpleName = c.getSimpleName();
            for (String suffix : deleteSuffix) {
                if (simpleName.endsWith(suffix)) {
                    return simpleName.substring(0, simpleName.lastIndexOf(suffix));
                }
            }
        }
        return c.getSimpleName();
    }

    private static StringBuilder getOneBuilder(Class<?> c, String[] deleteSuffix) {
        return new StringBuilder("\r\nexport interface " + deleteSuffix(c, deleteSuffix) + " {\r\n");
    }

    private static void generator(Class<?> c, String[] deleteSuffix, boolean createAllInnerCascade, Class<?>[] cascadeClasses) {
        if (CREATING_CLASS_SET.contains(c)) {
            return;
        }
        CREATING_CLASS_SET.add(c);
        StringBuilder builder = getOneBuilder(c, deleteSuffix);
        BUILDERS.add(builder);
        Field[] declaredFields = c.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Class<?> type = declaredField.getType();
            if (type.isAssignableFrom(Map.class)) {
                continue;
            }
            if (normalType(type)) {
                normalTypeAppend(builder, declaredField, type, false);
            } else if (type.isAssignableFrom(List.class)) {
                Type genericType = declaredField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = ( ParameterizedType ) genericType;
                    Class<?> c2 = ( Class<?> ) pt.getActualTypeArguments()[0];
                    if (normalType(c2)) {
                        normalTypeAppend(builder, declaredField, c2, true);
                    } else {
                        handleJavaBean(builder, declaredField, c2, true, deleteSuffix,createAllInnerCascade,cascadeClasses);
                    }
                }
            } else {
                handleJavaBean(builder, declaredField, type, false, deleteSuffix,createAllInnerCascade,cascadeClasses);
            }
        }
        builder.append("}");
    }

    private static void handleJavaBean(StringBuilder builder,Field declaredField,Class<?> type, boolean array, String[] deleteSuffix, boolean createAllInnerCascade, Class<?>[] cascadeClasses){
        javaBeanAppend(builder, declaredField, type, array, deleteSuffix);
        if (cascade(type,createAllInnerCascade,cascadeClasses)) {
            generator(type, deleteSuffix, createAllInnerCascade, cascadeClasses);
        }
    }

    private static boolean cascade(Class<?> type,boolean createAllInnerCascade, Class<?>[] cascadeClasses){
        return cascadeClasses != null && Arrays.asList(cascadeClasses).contains(type) || createAllInnerCascade;
    }


    private static boolean normalType(Class<?> c) {
        return c == BigDecimal.class || c == String.class || c.isPrimitive() || isBaseTypePackaging(c);
    }


    private static boolean isBaseTypePackaging(Class<?> c) {
        return c.equals(Byte.class) || c.equals(Integer.class) || c.equals(Long.class) || c.equals(Double.class) || c.equals(Float.class) || c.equals(Character.class) || c.equals(Short.class) || c.equals(Boolean.class);
    }


    private static void appendToFile(String filePath, String content) {
        File file = new File(filePath);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            //将写文件指针移到文件尾。
            raf.seek(raf.length());
            raf.writeBytes(content);
        }catch (Exception e){
            log.error("写文件异常{}",e.getMessage());
        }
    }

    /**
     * @param appendToFile  是否将生成的interface添加到ts文件中
     * @param filePath ts文件路径 ，不存在自动创建.仅当appendToFile为true时有效
     * @param c 需要生成的ts的interface对应的java class
     * @param deleteSuffix 需要删除的interface 名称的后缀；例如:UserDTO需要生成User,则传入new String[]{"DTO"}
     * @param createAllInnerCascade 是否级联生成碰到的所有java bean
     * @param cascadeClasses 仅当createAllInnerCascade为false时，此参数有效。表示需要级联生成的java bean的class数组,可以为空
     */
    public static void generator(boolean appendToFile,String filePath, Class<?> c, String[] deleteSuffix, boolean createAllInnerCascade, Class<?>[] cascadeClasses) {
        generator(c, deleteSuffix, createAllInnerCascade, cascadeClasses);
        for (StringBuilder builder : TsInterfaceGenerator.BUILDERS) {
            log.info(builder.toString());
        }
        if (appendToFile) {
            String content = String.join("", BUILDERS);
            appendToFile(filePath,content);
        }
    }

}

