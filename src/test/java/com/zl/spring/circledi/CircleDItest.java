package com.zl.spring.circledi;

import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CircleDItest {
	
	@SuppressWarnings("spring/resource")
	@Test(expected = BeanCurrentlyInCreationException.class)
	public void testCircleByConstructor() throws Throwable {
	try {
	      new ClassPathXmlApplicationContext("circleInjectByConstructor.xml");
	    }
	    catch (Exception e) {
	      //因为要在创建circle3时抛出；
	      Throwable e1 = e.getCause().getCause().getCause();
	      e1.printStackTrace();
	      throw e1;
	    }
	}
	
	@Test(expected = BeanCurrentlyInCreationException.class)
	public void testCircleBySetterAndPrototype () throws Throwable {  
	    try {  
	        @SuppressWarnings("spring/resource") ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
	"circleInjectBySetterAndPrototype.xml");  
	        System.out.println(ctx.getBean("circleA"));  
	    }  
	    catch (Exception e) {  
	        Throwable e1 = e.getCause().getCause().getCause();  
	        throw e1;  
	    }  
	}  
}
