package com.zl.tomcat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjie
 * @version 2018/11/9
 */
public class ServletMappingConfig {
    public static List<ServletMapping> servletMappingList =new ArrayList<>();
    //制定哪个URL交给哪个servlet来处理
    static{
        servletMappingList.add(new ServletMapping("findGirl","/girl","com.zl.tomcat.FindGirlServlet"));
        servletMappingList.add(new ServletMapping("helloWorld","/world","com.zl.tomcat.HelloWorldServlet"));
    }
}
