package com.zl.spring.di;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Snippet {
	@Test
	public void testConstructorDependencyInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("constructorDI.xml");
		// 获取根据参数索引依赖注入的Bean
		HelloApi byIndex = beanFactory.getBean("byIndex", HelloApi.class);
		byIndex.sayHello();
		// 获取根据参数类型依赖注入的Bean
		HelloApi byType = beanFactory.getBean("byType", HelloApi.class);
		byType.sayHello();
		// 获取根据参数名字依赖注入的Bean
		HelloApi byName = beanFactory.getBean("byName", HelloApi.class);
		byName.sayHello();
	}

	@Test
	public void testStaticFactoryDependencyInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("staticfactoryDI.xml");
		// 获取根据参数索引依赖注入的Bean
		HelloApi byIndex = beanFactory.getBean("byIndex", HelloApi.class);
		byIndex.sayHello();
		// 获取根据参数类型依赖注入的Bean
		HelloApi byType = beanFactory.getBean("byType", HelloApi.class);
		byType.sayHello();
		// 获取根据参数名字依赖注入的Bean
		HelloApi byName = beanFactory.getBean("byName", HelloApi.class);
		byName.sayHello();
	}

	@Test
	public void testInstanceFactoryDependencyInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("instancefactoryDI.xml");
		// 获取根据参数索引依赖注入的Bean
		HelloApi byIndex = beanFactory.getBean("byIndex", HelloApi.class);
		byIndex.sayHello();
		// 获取根据参数类型依赖注入的Bean
		HelloApi byType = beanFactory.getBean("byType", HelloApi.class);
		byType.sayHello();
		// 获取根据参数名字依赖注入的Bean
		HelloApi byName = beanFactory.getBean("byName", HelloApi.class);
		byName.sayHello();
	}

	@Test
	public void testSetterDependencyInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("bean/bean.xml");
		HelloApi bean = beanFactory.getBean("bean", HelloApi.class);
		bean.sayHello();
	}

	@Test
	public void testListInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("listmaparrayDI.xml");
		ListTestBean listBean = beanFactory.getBean("listBean", ListTestBean.class);
		System.out.println(listBean.getValues().size());
		Assert.assertEquals(3, listBean.getValues().size());
	}

	@Test
	public void testArrayInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("listmaparrayDI.xml");
		ArrayTestBean arrayBean = beanFactory.getBean("arrayBean", ArrayTestBean.class);
		System.out.println(arrayBean.getArray().length);
		System.out.println(arrayBean.getArray2().length);
		Assert.assertEquals(3, arrayBean.getArray().length);
	}

	@Test
	public void testMapInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("listmaparrayDI.xml");
		MapTestBean mapBean = beanFactory.getBean("mapBean", MapTestBean.class);
		System.out.println(mapBean.getValues().size());
	}

	@Test
	public void testPropertiesInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("listmaparrayDI.xml");
		PropertiesTestBean propertiesBean = beanFactory.getBean("propertiesBean", PropertiesTestBean.class);
		System.out.println(propertiesBean.getValues().size());
	}

	@Test
	public void testBeanInject() {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("refbeanDI.xml");
		// 通过构造器方式注入
		HelloApiDecorator bean1 = beanFactory.getBean("bean1", HelloApiDecorator.class);
		bean1.sayHello();
		System.out.println(bean1.getHelloApi().getClass().getSimpleName());
		// 通过setter方式注入
		HelloApiDecorator bean2 = beanFactory.getBean("bean2", HelloApiDecorator.class);
		bean2.sayHello();
		System.out.println(bean2.getHelloApi().getClass().getSimpleName());

	}

	@Test
	public void testLocalAndparentBeanInject() {
		// 初始化父容器
		ApplicationContext parentBeanContext = new ClassPathXmlApplicationContext("parentBeanInject.xml");
		// 初始化当前容器
		ApplicationContext beanContext = new ClassPathXmlApplicationContext(
				new String[] { "reflocalorrefparentDI.xml" }, parentBeanContext);
		// HelloApi bean1 = beanContext.getBean("bean1", HelloApi.class);
		// bean1.sayHello();//该Bean引用local bean
		HelloApiDecorator bean2 = beanContext.getBean("bean2", HelloApiDecorator.class);
		bean2.sayHello();// 该Bean引用parent bean
	}

	@Test
	public void testInnerBeanInject() {
		ApplicationContext context = new ClassPathXmlApplicationContext("insidebeanDI.xml");
		HelloApiDecorator bean = context.getBean("bean", HelloApiDecorator.class);
		bean.sayHello();
		System.out.println(bean.getHelloApi().getClass().getSimpleName());
		HelloApi api = bean.getHelloApi();
		api.sayHello();
	}

	// 对象图导航
	@Test
	public void testNavigationBeanInject() {
		ApplicationContext context = new ClassPathXmlApplicationContext("navigationDI.xml");
		NavigationA navigationA = context.getBean("a", NavigationA.class);
		navigationA.getNavigationB().getNavigationC().sayNavigation();
		navigationA.getNavigationB().getList().get(0).sayNavigation();
		navigationA.getNavigationB().getMap().get("key").sayNavigation();
		navigationA.getNavigationB().getArray()[0].sayNavigation();
		(( NavigationC ) navigationA.getNavigationB().getProperties().get("1")).sayNavigation();
	}

	@Test
	public void testnamespaceInject() {
		ApplicationContext context = new ClassPathXmlApplicationContext("namespaceDI.xml");
		IdRefTestBean bean1 = context.getBean("idrefBean1", IdRefTestBean.class);
		System.out.println(bean1.getId());
		IdRefTestBean bean2 = context.getBean("idrefBean2", IdRefTestBean.class);
		System.out.println(bean2.getId());

	}

	@Test
	public void testAutowireByName() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire-byName.xml");
		HelloApiDecorator helloApi = context.getBean("bean", HelloApiDecorator.class);
		helloApi.sayHello();
	}
	
	@Test
	public void testAutowireByType() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire-byType.xml");
		HelloApiDecorator helloApi = context.getBean("bean", HelloApiDecorator.class);
		helloApi.sayHello();
	}

}
