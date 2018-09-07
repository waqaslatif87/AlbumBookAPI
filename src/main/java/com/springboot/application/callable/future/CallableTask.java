
package com.springboot.application.callable.future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.springboot.application.model.APIResponseItem;
import com.springboot.application.model.Album;
import com.springboot.application.model.AlbumServiceResponse;
import com.springboot.application.model.Book;
import com.springboot.application.model.BookServiceResponse;
import com.springboot.application.model.EResourceType;

/**
 * Implementation of {@link Callable} for executing the tasks concurrently.
 * 
 * @author Waqas
 *
 */

public class CallableTask implements Callable<List<APIResponseItem>> {

	private RestTemplate restTemplate;
	private String requestURL;
	private Class<?> objectType;

	/**
	 * Default constructor is private so that Instance of {@link CallableTask}
	 * Shouldn't be created with the default constructor.
	 */
	@SuppressWarnings("unused")
	private CallableTask() {
	}

	/**
	 * Overloaded constructor to create an instance of {@link CallableTask} with the
	 * required inputs to perform tasks.
	 * 
	 * @param restTemplate
	 * @param requestURL
	 * @param objectType
	 * @param meterRegistry
	 */
	public CallableTask(final RestTemplate restTemplate, final String requestURL, final Class<?> objectType) {
		this.restTemplate = restTemplate;
		this.requestURL = requestURL;
		this.objectType = objectType;
	}

	/**
	 * Perform submitted task to the {@link CallableTask} and Return response when
	 * the task is completed and fut.get() method of future is called. This method
	 * will hit the Rest endpoint provided with the task submitted to the future and
	 * get the response from REST Endpoint and then transform it According to the
	 * Search API expected Response.
	 * 
	 * @return {@link List} of {@link APIResponseItem}
	 */
	@Override
	public List<APIResponseItem> call() throws Exception {

		final List<APIResponseItem> list = new ArrayList<APIResponseItem>();

		if (objectType == AlbumServiceResponse.class) {

			AlbumServiceResponse albumAPIResponse = (AlbumServiceResponse) restTemplate.getForObject(requestURL,
					this.objectType);

			// check If the response is not null and results are not empty, then
			// make a steam of results and transform it in to the expected API response
			// format.

			if (albumAPIResponse != null && !CollectionUtils.isEmpty(albumAPIResponse.getResults())) {
				List<APIResponseItem> albums = albumAPIResponse.getResults().stream()
						.map(this::getResponseItemFromAlbumWrapper).collect(Collectors.toList());
				list.addAll(albums);
			}
		} else if (objectType == BookServiceResponse.class) {

			BookServiceResponse bookAPIResponse = (BookServiceResponse) restTemplate.getForObject(requestURL,
					this.objectType);

			// check If the response is not null and results are not empty, then
			// make a steam of results and transform it in to the expected API response
			// format.

			if (bookAPIResponse != null && !CollectionUtils.isEmpty(bookAPIResponse.getItems())) {
				List<APIResponseItem> books = bookAPIResponse.getItems().stream()
						.map(this::getResponseItemFromBookWrapper).collect(Collectors.toList());
				list.addAll(books);
			}
		}

		return list;
	}

	/**
	 * Create an Instance of {@link APIResponseItem} using {@link Album} object
	 * passed as an input and return it.
	 * 
	 * @param albumWrapperObj
	 * @return
	 */
	private APIResponseItem getResponseItemFromAlbumWrapper(Album albumWrapperObj) {
		List<String> artists = Arrays.asList(albumWrapperObj.getArtistName());
		return new APIResponseItem(albumWrapperObj.getTrackName(), artists, EResourceType.ALBUM);
	}

	/**
	 * Create an Instance of {@link APIResponseItem} using {@link Book} object
	 * passed as an input and return it.
	 * 
	 * @param albumWrapperObj
	 * @return
	 */
	private APIResponseItem getResponseItemFromBookWrapper(Book bookWrapper) {
		return new APIResponseItem(bookWrapper.getVolumeInfo().getTitle(), bookWrapper.getVolumeInfo().getAuthors(),
				EResourceType.BOOK);
	}

}
