package com.zl.spring.di;
public class DependencyInjectByInstanceFactory {  
    public HelloApi newInstance(String message, int index) {  
        return new HelloImpl3(message, index);  
    }  
} 