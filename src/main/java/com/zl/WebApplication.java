package com.zl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author tzxx
 */
@EnableCaching
@SpringBootApplication
@EnableWebSocket
public class WebApplication {


	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
