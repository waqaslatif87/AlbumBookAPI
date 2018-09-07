# Spring Boot Application - Search Albums and Books

This Application used Spring-Boot and JAVA-8 and exposed a service that will accept a request with text parameter on input.
It will return maximum of 5 books and maximum of 5 albums that are related to the input term. The response elements will only contain title, authors(/artists) and information whether it's a book or an album.
 
The iTunes API which is used for searching Albums: 
https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching
 
The Google Books API which is used for searching books: 
please use Google Books API: https://developers.google.com/books/docs/v1/reference/volumes/list 

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites
For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.springboot.application.Application` class from your IDE.


----
    package com.springboot.application;
    import java.util.ArrayList;
    import java.util.List;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.web.client.RestTemplateBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.http.converter.HttpMessageConverter;
    import org.springframework.web.client.RestTemplate;

    @SpringBootApplication
    public class Application {
    	@Autowired
    	RestTemplateBuilder restTemplateBuilder;
    
    	@Bean
    	public RestTemplate createRestTemplate() {
    		RestTemplate restTemplate = restTemplateBuilder.build();
    		List<HttpMessageConverter<?>> msgConverters = new ArrayList<HttpMessageConverter<?>>(1);
    		msgConverters.add(new MappingAnyJsonHttpMessageConverter());
    		restTemplate.setMessageConverters(msgConverters);
    		return restTemplate;
    	}
    	public static void main(String[] args) {
    		SpringApplication.run(Application.class, args);
    	}
    }
----



Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
## Search Albums and Books API
The Application exposed a REST Endpoint for searching Albums and Books from their respective upstream services and return the results.This REST Endpoint will accept a request with text parameter on input called term and It will return maximum of 5 books and maximum of 5 albums that are related to the input term.
API Request:
REST Endpoint URL : http://localhost:8080/api/list?term=jack_johnson
Http Method : GET

Upon hitting the above URL by having a query parameter called term with a value "jack_johnson" the JSON Response mentioned below will be returned as an outcome which will have maximum 5 books and 5 albums related to the provided term and also these entries are sorted by title in ascending order.

API Respose:
```
[
  {
    "title": "Banana Pancakes",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "ALBUM"
  },
  {
    "title": "Better Together",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "ALBUM"
  },
  {
    "title": "Good People",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "ALBUM"
  },
  {
    "title": "Jack Johnson",
    "authorsOrArtists": [
      "Sal Fradella"
    ],
    "resourceType": "BOOK"
  },
  {
    "title": "Jack Johnson is a dandy",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "BOOK"
  },
  {
    "title": "Jack Johnson, Rebel Sojourner",
    "authorsOrArtists": [
      "Theresa Runstedtler"
    ],
    "resourceType": "BOOK"
  },
  {
    "title": "My Life and Battles",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "BOOK"
  },
  {
    "title": "My Life in the Ring and Out",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "BOOK"
  },
  {
    "title": "Sitting, Waiting, Wishing",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "ALBUM"
  },
  {
    "title": "Upside Down",
    "authorsOrArtists": [
      "Jack Johnson"
    ],
    "resourceType": "ALBUM"
  }
]
```
## Swagger UI - API Documentation
In this Application the swagger UI is also configured for the API documentation purpose. SwaggerUIController exposed the SwaggerUI with the context path ("/")

Swagger UI URL : http://localhost:8080/swagger-ui.html

The Swagger UI can be used to send the request to the REST API in order get the response of Search Albums and Books operation.
In Swagger UI, Under album-book-search-controller a GET method with the path '/api/list' should be listed in operations. Use this operation in order to search the albums and books by providing the input parameter term and click button Try it out! 
The request has been sent and response will be returned in the response body.


## Monitoring
For Monitoring purpose Spring Actuator is used in this application. Actuator brings production-ready features to the application. Actuator in this application is mainly used to expose the Health and performance metrices of the running API and Application.
Actuator server is configured in another tomcat with a diferrent using the property 'management.server.port=8081' defined in application.properties file.

Actuator Server URL : http://localhost:8081/actuator

Hitting this URL in the browser will give you the details about the endpoints which is exposed through the configuration.

##### Endpoints:
Here are the two endpoints of actualtor which is exposed and configured in this application by defining the configuration property 'management.endpoints.web.exposure.include=metrics,health' in application.properties file. This value of the property enabled health and metrics endpoint for the Application.

- http://localhost:8081/actuator/health - Shows health information of running API and application.
 ----
    {
      "status": "UP",
      "details": {
        "APIHealthCheck": {
          "status": "UP",
          "details": {
            "Service Status": "Service is up and running."
          }
        },
        "diskSpace": {
          "status": "UP",
          "details": {
            "total": 254735806464,
            "free": 160758030336,
            "threshold": 10485760
          }
        }
      }
    }
----
APIHealthCheck object gives you the status of the Running API which is exposed to search Albums and Books. The above outcome is achieved by customizing Actuator Health Endpoint by implementing APIHealthCheck.Java class.  

- http://localhost:8081/actuator/metrics – Shows ‘metrics’ information of the running API and application. This endpoint will list down the default metrics which is provided by the actuator as well as the custom metrics which is defined in the code.
----
    {
    "names": [
        "jvm.buffer.memory.used",
        "jvm.memory.used",
        "jvm.gc.memory.allocated",
        "jvm.memory.committed",
        "tomcat.sessions.created",
        "tomcat.sessions.expired",
        "tomcat.global.request.max",
        "tomcat.global.error",
        "jvm.gc.max.data.size",
        "logback.events",
        "system.cpu.count",
        "jvm.memory.max",
        "album.service.response.time.in.seconds",
        "jvm.buffer.total.capacity",
        "jvm.buffer.count",
        "jvm.threads.daemon",
        "process.start.time",
        "book.service.response.time.in.seconds",
        "tomcat.global.sent",
        "tomcat.sessions.active.max",
        "tomcat.threads.config.max",
        "jvm.gc.live.data.size",
        "process.cpu.usage",
        "tomcat.servlet.request",
        "jvm.gc.pause",
        "process.uptime",
        "http.client.requests",
        "tomcat.global.received",
        "tomcat.cache.hit",
        "tomcat.servlet.error",
        "tomcat.servlet.request.max",
        "tomcat.cache.access",
        "tomcat.threads.busy",
        "tomcat.sessions.active.current",
        "system.cpu.usage",
        "jvm.threads.live",
        "jvm.classes.loaded",
        "jvm.classes.unloaded",
        "http.server.requests",
        "jvm.threads.peak",
        "tomcat.threads.current",
        "tomcat.global.request",
        "jvm.gc.memory.promoted",
        "tomcat.sessions.rejected",
        "tomcat.sessions.alive.max"
      ]
    }
----

There are two custom metrices in defined in this application. 
    -- book.service.response.time.in.seconds : This metrics tells you the Book upstream service response time in seconds.
    -- album.service.response.time.in.seconds : This metrics tells you the Album upstream service response time in seconds.
    
Endpoint To Monitor the response time in seconds of book service : http://localhost:8081/actuator/metrics/book.service.response.time.in.seconds

----
    {
      "name": "book.service.response.time.in.seconds",
      "measurements": [
        {
          "statistic": "VALUE",
          "value": 1.28
        }
      ],
      "availableTags": [
        
      ]
    }
----
Endpoint To Monitor the response time in seconds of Album service : http://localhost:8081/actuator/metrics/album.service.response.time.in.seconds
----
    {
      "name": "album.service.response.time.in.seconds",
      "measurements": [
        {
          "statistic": "VALUE",
          "value": 0.842
        }
      ],
      "availableTags": [
        
      ]
    }
----

## profile

Application used spring.profiles.active Environment property in the application.properties file to specify which profiles are active. By default dev profile is active. 
``
    spring.profiles.active=dev
``

application-dev.properties File :

----
    result.limit=5
    album.rest.url=https://itunes.apple.com/search
    book.rest.url=https://www.googleapis.com/books/v1/volumes
    #actuator configuration
    #actuator server port
    management.server.port=8081
    #actuator exposed endpoints
    management.endpoints.web.exposure.include=metrics,health
    # HEALTH ENDPOINT
    management.endpoint.health.show-details=always

    #Server Ip Address and port in which the application is deployed
    server.ip=127.0.0.1
    server.port=8080

    #logging properties
    logging.level.root=info
----
## Running the Integration tests

In this application the integration tests has been written to test the REST API. In order to run these test you can use this command.

``
	mvn clean verify
``


## Authors
* **Waqas Latif** (email: waqaslatif88@outlook.com)

