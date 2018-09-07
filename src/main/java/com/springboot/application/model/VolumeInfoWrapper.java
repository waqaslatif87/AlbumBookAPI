package com.springboot.application.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class Represents the Volume Information of the Book {@link Book}
 * 
 * @author Waqas
 *
 */
public class VolumeInfoWrapper implements Serializable {

	private static final long serialVersionUID = 7876219118467346674L;

	private String title;

	private List<String> authors;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
}
