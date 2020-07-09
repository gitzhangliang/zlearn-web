package com.zl.spring.expression;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {  
    @Test  
    public void string() {  
    	String s = "hello";
    	System.out.println(s.concat("l"));
    } 
    @Test  
    public void helloWorld() {  
        ExpressionParser parser = new SpelExpressionParser();  
        Expression expression =  
parser.parseExpression("('Hello' + ' World').concat(#end)");  
        EvaluationContext context = new StandardEvaluationContext(); 
        context.setVariable("end", "!"); 
        System.out.println(expression.getValue(context));
        Assert.assertEquals("Hello World!", expression.getValue(context));  
    }  
}