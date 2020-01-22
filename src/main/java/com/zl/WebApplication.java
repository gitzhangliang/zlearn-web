package com.zl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author tzxx
 */
@EnableCaching
@SpringBootApplication
@EnableWebSocket
@MapperScan("com.zl.mapper")
@ServletComponentScan
public class WebApplication {

	@Value(value = "${logging.config}")
	private String log;
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
