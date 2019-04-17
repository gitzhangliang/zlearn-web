package com.zl.config.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author tzxx
 */
@Configuration
@ConditionalOnClass(value = { RestTemplate.class, HttpClient.class })
public class RestTemplateConfiguration {
	/**
	 * 连接超时默认3s
	 */
	private int connectTimeout=3000;
	/**
	 * 读取超时默认30s
	 */
	private int readTimeout=30000;


	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory){
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(this.connectTimeout);
		factory.setReadTimeout(this.readTimeout);
		return factory;
	}


}