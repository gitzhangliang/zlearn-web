package com.zl.spring.otherdi;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestDI {

		@Test
		public void testDependOn() throws IOException {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("depends-on.xml");
			// 一点要注册销毁回调，否则我们定义的销毁方法不执行
			context.registerShutdownHook();
			DependentBean dependentBean = context.getBean("dependentBean", DependentBean.class);
			dependentBean.write("aaa");
		}

}
