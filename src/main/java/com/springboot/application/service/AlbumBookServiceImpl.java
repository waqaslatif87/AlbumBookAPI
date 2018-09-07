package com.springboot.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.springboot.application.callable.future.CallableTask;
import com.springboot.application.custom.exception.DataNotFoundException;
import com.springboot.application.model.APIResponseItem;
import com.springboot.application.model.AlbumServiceResponse;
import com.springboot.application.model.BookServiceResponse;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * 
 * Implementation of {@link IAlbumBookService}
 * 
 * @author Waqas
 *
 */

@Service
public class AlbumBookServiceImpl implements IAlbumBookService {

	private static final Logger log = LoggerFactory.getLogger(AlbumBookServiceImpl.class);

	private static final String QUERY_PARAM_LIMIT = "limit";
	private static final String QUERY_PARAM_MAX_RESULTS = "maxResults";
	private static final String QUERY_PARAM_Q = "q";
	private static final String QUERY_PARAM_TERM = "term";

	@Value("${album.rest.url}")
	String albumRestUrl;
	@Value("${book.rest.url}")
	String bookRequestUrl;
	@Value("${result.limit}")
	Integer resultLimit;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	MeterRegistry meterRegistry;

	@Override
	public List<APIResponseItem> findByTerm(String param) throws Exception {

		try {

			param = java.net.URLEncoder.encode(param, "UTF-8");

			log.debug("Request received with input term : {}", param);

			// The below line is Creating Executor service by using utility
			// methods of the Executors class with the thread pool size of 5.

			final ExecutorService executor = Executors.newFixedThreadPool(5);

			// creating a list of Future of type <List<APIResponseItem>>
			final List<Future<List<APIResponseItem>>> futureList = new ArrayList<Future<List<APIResponseItem>>>();
			final List<APIResponseItem> responseList = new ArrayList<APIResponseItem>();

			// build rest URL for listing Album with query params.
			final UriComponentsBuilder albumRequestUrlbuilder = UriComponentsBuilder.fromUriString(albumRestUrl)
					.queryParam(QUERY_PARAM_TERM, param).queryParam(QUERY_PARAM_LIMIT, resultLimit);

			// Used executor.submit() for submitting the tasks to the executor which starts
			// the task execution and returns a Future object and the result can be
			// accessed via the returned Future object.

			final Future<List<APIResponseItem>> albumRequestFut = executor.submit(new CallableTask(restTemplate,
					albumRequestUrlbuilder.build().toUri().toString(), AlbumServiceResponse.class));
			futureList.add(albumRequestFut);

			// build rest URL for listing the books with query params.
			final UriComponentsBuilder bookRequestUrlbuilder = UriComponentsBuilder.fromUriString(bookRequestUrl)
					.queryParam(QUERY_PARAM_Q, param).queryParam(QUERY_PARAM_MAX_RESULTS, resultLimit);

			final Future<List<APIResponseItem>> bookRequestFut = executor.submit(new CallableTask(restTemplate,
					bookRequestUrlbuilder.build().toUri().toString(), BookServiceResponse.class));
			futureList.add(bookRequestFut);

			for (final Future<List<APIResponseItem>> future : futureList) {
				try {
					responseList.addAll(future.get(3, TimeUnit.SECONDS));
				} catch (Exception ex) {
					log.error("Error occurred while getting response from future. " + ex.getMessage(), ex);
					continue;
				}
			}

			// throw DataNotFoundException if the Albums and Books are not found against the
			// provided term.
			if (CollectionUtils.isEmpty(responseList)) {
				throw new DataNotFoundException("Data not found for input Term : " + param);
			}
			// Sorts the list of APIResponseItem according to the compareTo method
			// implementation provided in
			// that class.
			Collections.sort(responseList);
			return responseList;
		} catch (Exception ex) {
			throw ex;
		}
	}

}
