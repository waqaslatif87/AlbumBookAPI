package com.springboot.application.service;

import java.util.List;

import com.springboot.application.model.APIResponseItem;
import com.springboot.application.model.Album;
import com.springboot.application.model.Book;

/**
 * Service exposed a methods to search objects of {@link Albums} and
 * {@link Book} by using input parameter term.
 * 
 * @author Waqas
 *
 */
public interface IAlbumBookService {
	/**
	 * This Method consumes two separate rest endpoints in order to search instances
	 * of {@link Album} and {@link Book} by input parameter term and return
	 * {@link List} of {@link APIResponseItem} objects which contained the instances
	 * of {@link Album} and {@link Book}.
	 * 
	 * @param term
	 * @return {@link List} of {@link APIResponseItem} objects.
	 * @throws Exception
	 */

	public List<APIResponseItem> findByTerm(String term) throws Exception;
}
