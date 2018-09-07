package com.springboot.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.springboot.application.httpmessage.converter.MappingAnyJsonHttpMessageConverter;
import com.springboot.application.interceptor.UpstreamServiceMonitoringInterceptor;

/**
 * This class acts as the launching point for
 * application. @SpringBootApplication as our primary application configuration
 * class, behind the scenes, that’s equivalent
 * to @Configuration, @EnableAutoConfiguration, and @ComponentScan together.
 * 
 * @author Waqas
 *
 */
@SpringBootApplication
public class Application {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	UpstreamServiceMonitoringInterceptor interceptor;

	/**
	 * Creating {@link RestTemplate} Bean with the custom Http Message Converter.
	 * 
	 * 
	 * @return {@link RestTemplate}
	 */
	@Bean
	public RestTemplate createRestTemplate() {
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		RestTemplate restTemplate = restTemplateBuilder.build();
		restTemplate.setRequestFactory(factory);
		List<HttpMessageConverter<?>> msgConverters = new ArrayList<HttpMessageConverter<?>>(1);
		msgConverters.add(new MappingAnyJsonHttpMessageConverter());
		restTemplate.setMessageConverters(msgConverters);
		restTemplate.setInterceptors(Collections.singletonList(interceptor));
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
