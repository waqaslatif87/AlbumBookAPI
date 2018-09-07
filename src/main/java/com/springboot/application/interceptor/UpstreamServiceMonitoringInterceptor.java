package com.springboot.application.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Interceptor which Monitors and create the metrics for Upstream service
 * response Time.
 * 
 * @author Waqas
 *
 */
@Component
public class UpstreamServiceMonitoringInterceptor implements ClientHttpRequestInterceptor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MeterRegistry meterRegistry;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		// capture time before the service request.
		double timeBeforeServiceReqInMillis = System.currentTimeMillis();

		ClientHttpResponse response = execution.execute(request, body);

		// capture time after the service response.
		double timeAfterServiceResponseInMillis = System.currentTimeMillis();

		double timeInSeconds = (timeAfterServiceResponseInMillis - timeBeforeServiceReqInMillis) / 1000;

		if (request.getURI().getPath().equals("/books/v1/volumes")) {

			// Making metric for Book upstream service response time in seconds using guage.
			meterRegistry.gauge("book.service.response.time.in.seconds", timeInSeconds);

			log.debug("Book service respose Time in Seconds : {}", timeInSeconds);

		} else if (request.getURI().toString().contains("https://itunes.apple.com/search")) {

			// Making metric for Album upstream service response time in seconds using
			// guage.
			meterRegistry.gauge("album.service.response.time.in.seconds", timeInSeconds);

			log.debug("Album service respose Time in Seconds : {}", timeInSeconds);
		}

		return response;
	}

}