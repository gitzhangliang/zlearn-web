package com.zl.spring.di;

public class HelloImpl4 implements HelloApi {  
    private String message;  
    private int index;  
//setter方法  
    public void setMessage(String message) {  
        this.message = message;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }  
    @Override  
    public void sayHello() {  
        System.out.println(index + ":" + message);  
    }  
}  