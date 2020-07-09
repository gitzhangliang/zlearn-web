package com.zl.spring.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tzxx
 */
public class ResourceTest {
	@Test
	public void testByteArrayResource() {
		Resource resource = new ByteArrayResource("Hello World!".getBytes());
		if (resource.exists()) {
			dumpStream(resource);
		}
		Assert.assertEquals(false, resource.isOpen());
	}

	private void dumpStream(Resource resource) {
		InputStream is = null;
		try {
			// 1.获取文件资源
			is = resource.getInputStream();
			// 2.读取资源
			byte[] descBytes = new byte[is.available()];
			is.read(descBytes);
			System.out.println(new String(descBytes));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 3.关闭资源
				is.close();
			} catch (IOException e) {
			}
		}
	}
	@Test
	public void testInputStreamResource() {
	   ByteArrayInputStream bis = new ByteArrayInputStream("Hello World!".getBytes());
	   Resource resource = new InputStreamResource(bis);
	    if(resource.exists()) {
	       dumpStream(resource);
	    }
	    Assert.assertEquals(true, resource.isOpen());
	}

	@Test
	public void testFileResource() {
	File file = new File("d:/test.txt");
	    Resource resource = new FileSystemResource(file);
	    if(resource.exists()) {
	        dumpStream(resource);
	    }
	    Assert.assertEquals(false, resource.isOpen());
	}

	@Test
	public void testClasspathResourceByDefaultClassLoader() throws IOException {
	   Resource resource = new ClassPathResource("test1.properties");
	    if(resource.exists()) {
	        dumpStream(resource);
	    }
	    System.out.println("path:" + resource.getFile().getAbsolutePath());
	    Assert.assertEquals(false, resource.isOpen());
	}

	@Test
	public void testClasspathResourceByClassLoader() throws IOException {
	    ClassLoader cl = this.getClass().getClassLoader();
	    Resource resource = new ClassPathResource("test1.properties" , cl);
	    if(resource.exists()) {
	        dumpStream(resource);
	    }
	    System.out.println("path:" + resource.getFile().getAbsolutePath());
	    Assert.assertEquals(false, resource.isOpen());
	}

	@Test
	public void testClasspathResourceByClass() throws IOException {
	   Class clazz = this.getClass();
/*	    Resource resource1 = new ClassPathResource("test1.properties" , clazz);
	    if(resource1.exists()) {
	        dumpStream(resource1);
	    }
	    System.out.println("path:" + resource1.getFile().getAbsolutePath());
	    Assert.assertEquals(false, resource1.isOpen());  */

	    Resource resource2 = new ClassPathResource("test1.properties" , this.getClass());
	    if(resource2.exists()) {
	        dumpStream(resource2);
	   }
	    System.out.println("path:" + resource2.getFile().getAbsolutePath());
	    Assert.assertEquals(false, resource2.isOpen());
	}

	@Test
	public void testResourceLoad() {
	    ResourceLoader loader = new DefaultResourceLoader();
	    Resource resource = loader.getResource("classpath:test1.properties");
	    //验证返回的是ClassPathResource
	    Assert.assertEquals(ClassPathResource.class, resource.getClass());
	    Resource resource2 = loader.getResource("file:test1.properties");
	    //验证返回的是ClassPathResource
	    Assert.assertEquals(UrlResource.class, resource2.getClass());
	    Resource resource3 = loader.getResource("test1.properties");
	    //验证返默认可以加载ClasspathResource
	    Assert.assertTrue(resource3 instanceof ClassPathResource);
	}

	@Test
	public void testApplicationContext() {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("resourceLoaderAware.xml");
	    ResourceBean resourceBean = ctx.getBean(ResourceBean.class);
	    ResourceLoader loader = resourceBean.getResourceLoader();
	    Assert.assertTrue(loader instanceof ApplicationContext);
	}

	@Test
	public void testResourceInject() {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("resourceInject.xml");
	    ResourceBean3 resourceBean1 = ctx.getBean("resourceBean1", ResourceBean3.class);
	    ResourceBean3 resourceBean2 = ctx.getBean("resourceBean2", ResourceBean3.class);
	    Assert.assertTrue(resourceBean1.getResource() instanceof ClassPathResource);
	    Assert.assertTrue(resourceBean2.getResource() instanceof ClassPathResource);
	}
}