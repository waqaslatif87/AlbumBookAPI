package com.springboot.application.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Response Item represents the {@link Album} or {@link Book} instance having
 * attributes (title,authors/artists,resource type), The Book and Album is
 * differentiated by {@link EResourceType} which is Enum and having values
 * (album,book). It class @implements comparable interface and override
 * compareTo method for sorting the list of this class instances by title field
 * order ascending
 * 
 * @author Waqas
 *
 */
public class APIResponseItem implements Comparable<APIResponseItem>, Serializable {

	private static final long serialVersionUID = -8046725142065667748L;
	private String title;
	private List<String> authorsOrArtists;
	private EResourceType resourceType;

	public APIResponseItem(final String title, final List<String> authorsOrArtists, final EResourceType resourceType) {
		this.title = title;
		this.authorsOrArtists = authorsOrArtists;
		this.resourceType = resourceType;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthorsOrArtists() {
		return authorsOrArtists;
	}

	public void setAuthorsOrArtists(List<String> authorsOrArtists) {
		this.authorsOrArtists = authorsOrArtists;
	}

	public EResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(EResourceType resourceType) {
		this.resourceType = resourceType;
	}

	// Comparator used to avoid NullPointerException and place the null values at
	// start and compare strings with ignoring case.
	private static Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareToIgnoreCase);

	private static Comparator<APIResponseItem> metadataComparator = Comparator.comparing(APIResponseItem::getTitle,
			nullSafeStringComparator);

	@Override
	public int compareTo(APIResponseItem o) {
		return metadataComparator.compare(this, o);
	}

}
