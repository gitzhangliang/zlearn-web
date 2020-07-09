package com.zl.utils;

import com.zl.domain.Coder;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CollectionUtil {

    public static <T> String listToString(List<T> strs, String join){
        if(strs == null || strs.size() == 0){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (T s : strs) {
            buffer.append(s.toString()+join);
        }
        return buffer.toString().substring(0,buffer.toString().length()-1);
    }

    public static <E> List<E> sort(List<E> list, String field, String sort) {
        StringBuffer method = new StringBuffer(field);
        String getMethod ="get"+firstLetterToUpper(method.toString());
        if(list.size()>1){
            Collections.sort(list, new Comparator<Object>() {
                @SuppressWarnings("unchecked")
                @Override
                public int compare(Object a, Object b) {
                    try {
                        Method m1 = ((E) a).getClass().getMethod(getMethod);
                        Method m2 = ((E) b).getClass().getMethod(getMethod);
                        if (sort != null && "desc".equals(sort)){
                            return -getCompareResult(a, b, m1, m2);
                        }else{
                            return getCompareResult(a, b, m1, m2);
                        }
                    } catch (Exception ne) {
                        ne.printStackTrace();
                        throw new RuntimeException(ne);
                    }
                }

                @SuppressWarnings("unchecked")
                private <E> int getCompareResult(Object a, Object b, Method m1, Method m2)
                        throws IllegalAccessException, InvocationTargetException {
                    Object aValue = m1.invoke(((E) a));
                    Object bValue = m1.invoke(((E) b));
                    if(aValue == null && bValue == null){
                        return 0;
                    }else{
                        if(aValue == null){
                            return -1;
                        }
                        if(bValue == null){
                            return 1;
                        }
                    }
                    return ((Comparable<Object>)aValue).
                            compareTo(((Comparable<Object>)bValue));
                }
            });
        }
        return list;
    }
    private static String firstLetterToUpper(String str){
        char[] array = str.toCharArray();
        array[0] -= 32;
        return String.valueOf(array);
    }

    public static <K,V> Map<K,V> listToMap(List<V> data,String field){
        Map<K,V> map = new HashMap<>(16);
        for (V datum : data) {
            String getMethod ="get"+firstLetterToUpper(field);
            try {
                Method m = datum.getClass().getMethod(getMethod);
                K aValue = (K)m.invoke(datum);
                map.put(aValue,datum);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return map;
    }
    public static <T> Map<String,List<Object>> rowColumnReverse(List<T> data, Class<T> clazz){
        Map<String,List<Object>> map = new HashMap<>(16);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            String getMethod ="get"+firstLetterToUpper(fieldName);
            for (T datum : data) {
                try {
                    Method m = clazz.getMethod(getMethod);
                    Object aValue = m.invoke(datum);
                    List<Object> list = map.computeIfAbsent(fieldName, f -> new ArrayList<>());
                    list.add(aValue);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }

        }
        return map;
    }

}
