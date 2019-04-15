package com.zl.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author tzxx
 * @date 2019/3/12.
 */
public class PropertiesUtil {
    /**
     * 返回Properties文件
     * @param path 路径
     * @return
     */
    public static Properties getProperties(String path) {
        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
        try {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            properties.load(reader);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**properties文件转map
     * @param path
     * @return
     */
    public static Map<String, String> getPropertiesToMap(String path) {
        Properties props = getProperties(path);
        return getPropertiesToMap(props);
    }

    /**properties文件转map
     * @param props
     * @return
     */
    public static Map<String, String> getPropertiesToMap(Properties props) {
        Map<String, String> map = new HashMap<String, String>(16);
        Enumeration<?> en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = en.nextElement().toString();
            String property = props.getProperty(key);
            map.put(key, property);
        }
        return map;
    }
}
