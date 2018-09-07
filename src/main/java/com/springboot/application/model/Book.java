package com.springboot.application.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class Represents the Book object contained in a {@link List} of Book
 * Service response {@link BookServiceResponse}
 * 
 * @author Waqas
 *
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 5604335013956902445L;
	private VolumeInfoWrapper volumeInfo;

	public VolumeInfoWrapper getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(VolumeInfoWrapper volumeInfo) {
		this.volumeInfo = volumeInfo;
	}
}
