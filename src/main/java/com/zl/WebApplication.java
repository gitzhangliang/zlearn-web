package com.zl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.loader.JarLauncher;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * @author zl
 */
@EnableCaching
@SpringBootApplication
@EnableWebSocket
@MapperScan({"com.zl.mapper","com.zl.job.dao"})
@ServletComponentScan
public class WebApplication {

	@Value(value = "${logging.config}")
	private String log;
	public static void main(String[] args) {
		ProtectionDomain protectionDomain = WebApplication.class.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		System.out.println(codeSource.getLocation().getPath());
		System.out.println(Arrays.asList(System.getProperty("java.class.path").split(";")).size());
		for (String s : Arrays.asList(System.getProperty("java.class.path").split(";"))) {
			System.out.println(s);
		}
	}
}
