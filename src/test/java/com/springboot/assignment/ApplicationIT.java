package com.springboot.assignment;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.application.Application;
import com.springboot.application.custom.exception.ErrorDetails;
import com.springboot.application.model.APIResponseItem;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

	@LocalServerPort
	private int port;

	@Value("${result.limit}")
	public int resultLimit;

	TestRestTemplate restTemplate;
	ObjectMapper mapper;

	@Before
	public void setup() {
		mapper = new ObjectMapper();
		restTemplate = new TestRestTemplate();
	}

	/**
	 * Unit test to verify the Successful Service Response. This test will verify
	 * the HttpStatus code which should be 200, Service response time which should
	 * be less than 3 seconds, the Result list size should be less than or equal to
	 * the configured size limit.
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRetrieveStudentCourse() throws JsonParseException, JsonMappingException, IOException {
		double timeBeforeServiceReqInMillis = System.currentTimeMillis();

		final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/list?term=jackjackson"),
				HttpMethod.GET, null, String.class);

		double timeAfterServiceResponseInMillis = System.currentTimeMillis();

		double reponseTimeInSeconds = (timeAfterServiceResponseInMillis - timeBeforeServiceReqInMillis) / 1000;

		// check the Http status code.
		Assert.assertEquals("Reponse should have http status code : 200.", HttpStatus.OK, response.getStatusCode());

		// check the response time should be in 3 seconds.
		Assert.assertTrue("Service response time should be less than 3 seconds but the recorded response time is : "
				+ reponseTimeInSeconds, reponseTimeInSeconds < 3);

		List<APIResponseItem> responseList = mapper.readValue(response.getBody(), List.class);

		// Check the List in response should not be Null
		Assert.assertNotNull("Result should not be null.", responseList);

		// Downstream service limit would be 2 times of a single up stream service
		// because its result is the combine result of Album and Book service and Album
		// and Book service both have same limit which is pre-configured as 5.
		int apiResultsLimit = (2 * resultLimit);

		// Check that the list in response that size should be less than the limit
		// configured.
		Assert.assertTrue("Results size should be less than or equal to the configured result limit.",
				responseList.size() <= apiResultsLimit);

	}

	/**
	 * Unit test to verify the error response when the Upstream services does not
	 * have any results on provided term, The service will return the error. This
	 * Test will verify the http status code which should be 404, verify Error
	 * message and details.
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testRetrieveStudentCourse_DataNotFoundException()
			throws JsonParseException, JsonMappingException, IOException {

		final ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/list?term=agdsagfdfhs"),
				HttpMethod.GET, null, String.class);

		// check the Http status code.
		Assert.assertEquals("Reponse should have http status code : 404.", HttpStatus.NOT_FOUND,
				response.getStatusCode());

		ErrorDetails errorDetail = mapper.readValue(response.getBody(), ErrorDetails.class);

		// Check the Error Object return should not be Null
		Assert.assertNotNull("Response should not be null.", errorDetail);

		// check the error message.
		Assert.assertEquals("This error message return in reponse was not expected",
				"Data not found for input Term : agdsagfdfhs", errorDetail.getMessage());

		// check the details which tells the URI on which this error message is
		// returned.
		Assert.assertEquals("The error detail return in response was not expected", "uri=/api/list",
				errorDetail.getDetails());

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}