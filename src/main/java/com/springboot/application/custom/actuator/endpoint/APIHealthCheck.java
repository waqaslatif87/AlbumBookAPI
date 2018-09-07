package com.springboot.application.custom.actuator.endpoint;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * This class customize the Actuator Health Endpoint, Whenever the Actuator
 * Health (actuator/health) endpoint is hit by the admin the Health method which
 * is overridden in this class will be triggered and this method will hit
 * another REST endpoint to check whether that endpoint is up and running or
 * that is down and return the status according to the response.
 * 
 * @author Waqas
 *
 */
@Component
public class APIHealthCheck implements HealthIndicator {

	// Server IP Address on which the Rest API is published.
	@Value("${server.ip}")
	private String serverIp;
	// port of the server
	@Value("${server.port}")
	private String serverPort;

	/**
	 * Method will return the Health status of configured rest service.
	 */
	@Override
	public Health health() {

		if (isRemoteServiceUp()) {
			return Health.up().withDetail("Service Status", "Service is up and running.").build();
		}

		return Health.down().withDetail("Service Status", "Service is down.").build();
	}

	/**
	 * This function hits the API and check its status, if the service is up and
	 * running it will return true otherwise false.
	 * 
	 * @return {@link Boolean}
	 */
	private Boolean isRemoteServiceUp() {

		try {
			final URL siteURL = new URL("http://" + serverIp + ":" + serverPort + "/api/list?term=jack");

			final HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(3000);
			connection.connect();
			int code = connection.getResponseCode();
			if (code == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}