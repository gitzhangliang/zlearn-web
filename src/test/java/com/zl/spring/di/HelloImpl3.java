package com.zl.spring.di;
public class HelloImpl3 implements HelloApi {  
    private String message;  
    private int index;  
    public HelloImpl3() {
		// TODO Auto-generated constructor stub
	}
    public HelloImpl3(String message, int index) {  
        this.message = message;  
        this.index = index;  
    }  
    @Override  
    public void sayHello() {  
        System.out.println(index + ":" + message+"3");  
    }  
}  