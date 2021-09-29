package com.challenge.inventory.exception;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({	
	"timestamp",
	"errorCode",
	"errorMessage"
}
	)
@Component
public class CustomException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorCode;
	String errorMessage;
	private Date timestamp;
	
	public CustomException() {
		
	}
	public CustomException(String errorCode, String errorMessage, Date timestamp) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.timestamp = timestamp;
	}
	public CustomException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
