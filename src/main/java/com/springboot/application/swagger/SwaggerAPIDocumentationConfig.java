package com.springboot.application.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for SWAGGER UI.
 * 
 * @author Waqas
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerAPIDocumentationConfig {
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Albums and Books")
				.description("This API returns the list of Albums and Books related to the input parameter Term. ")
				.termsOfServiceUrl("").version("0.0.1-SNAPSHOT").build();
	}

	@Bean
	public Docket configureControllerPackageAndConvertors() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.springboot.application")).build()
				.directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class).apiInfo(apiInfo());
	}
}