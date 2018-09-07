package com.springboot.application.custom.exception;

import java.io.Serializable;
import java.util.Date;

/**
 * Response Bean to use when exceptions are thrown from API.
 * 
 * @author Waqas
 *
 */
public class ErrorDetails implements Serializable {

	private static final long serialVersionUID = 1262603140793418233L;
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails() {
	}

	/**
	 * Overloaded constructor to create instance of {@link ErrorDetails}
	 * 
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.setTimestamp(timestamp);
		this.setMessage(message);
		this.setDetails(details);
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}