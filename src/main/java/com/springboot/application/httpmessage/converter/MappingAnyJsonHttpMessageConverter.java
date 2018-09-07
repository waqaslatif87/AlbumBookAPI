package com.springboot.application.httpmessage.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Custom Http Message Converter, It convert media type text/javascript and
 * application/json;charset=UTF-8 in to java Objects.
 * 
 * @author Waqas
 *
 */
public class MappingAnyJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

	/**
	 * Adding supported media type for the custom Http Message Converter.
	 */
	public MappingAnyJsonHttpMessageConverter() {
		List<MediaType> list = new ArrayList<MediaType>(2);
		// adding Album Rest API content type which is text/javascript; charset=utf-8
		list.add(MediaType.parseMediaType("text/javascript; charset=utf-8"));
		// adding Book Rest API content type which is application/json;charset=UTF-8
		list.add(MediaType.parseMediaType("application/json;charset=UTF-8"));
		this.setSupportedMediaTypes(list);
	}
}