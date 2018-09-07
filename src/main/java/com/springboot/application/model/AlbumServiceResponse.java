package com.springboot.application.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the Response object of Album upstream service.It
 * contains the {@link List} of {@link Album}
 * 
 * @author Waqas
 *
 */
public class AlbumServiceResponse implements Serializable {

	private static final long serialVersionUID = -1909855878168894241L;

	private List<Album> results;

	public List<Album> getResults() {
		return results;
	}

	public void setResults(List<Album> results) {
		this.results = results;
	}

}
