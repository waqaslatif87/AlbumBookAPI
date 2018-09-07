package com.springboot.application.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the Response object of Book upstream service. It
 * contains the {@link List} of {@link Book}
 * 
 * @author Waqas
 *
 */
public class BookServiceResponse implements Serializable {

	private static final long serialVersionUID = 2433073577511004484L;
	private List<Book> items;

	public List<Book> getItems() {
		return items;
	}

	public void setItems(List<Book> items) {
		this.items = items;
	}
}
