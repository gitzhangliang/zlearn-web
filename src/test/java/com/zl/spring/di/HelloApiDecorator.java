package com.zl.spring.di;

public class HelloApiDecorator  {
	private HelloApi helloApi;
	private String name;
	private HelloApiDecorator had;
	
	public HelloApiDecorator getHad() {
		return had;
	}

	public void setHad(HelloApiDecorator had) {
		this.had = had;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 空参构造器
	public HelloApiDecorator() {
	}

	// 有参构造器
	public HelloApiDecorator(HelloApi helloApi) {
		this.helloApi = helloApi;
	}

	public void setHelloApi(HelloApi helloApi) {
		this.helloApi = helloApi;
	}

	public HelloApi getHelloApi() {
		return helloApi;
	}

	
	public void sayHello() {
		System.out.println("==========装饰一下===========");
		helloApi.sayHello();
		System.out.println(this.getName());
		System.out.println("==========装饰一下===========");
	}
}