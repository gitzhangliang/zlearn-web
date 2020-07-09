package com.zl.template;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;


/**
 * @author zhangliang
 * @date 2019/10/16.
 */
@Slf4j
public class ThymeleafUtil {
    private ThymeleafUtil(){}

    public static String constructHtml(Map<String,Object> dataMap,String templateName){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //构造上下文(Model)
        Context context = new Context();
        dataMap.forEach(context::setVariable);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        //渲染模板
        templateEngine.process(templateName, context, writer);
        //输出html
        String htmlStr = stringWriter.toString();
        return htmlStr;

    }

}
