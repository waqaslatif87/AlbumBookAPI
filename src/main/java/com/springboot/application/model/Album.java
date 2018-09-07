package com.springboot.application.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class Represents the Album object contained in a {@link List} of Album
 * Service response {@link AlbumServiceResponse}
 * 
 * @author Waqas
 *
 */
public class Album implements Serializable {

	private static final long serialVersionUID = -1417841184853294207L;
	private String trackName;
	private String artistName;

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
}
