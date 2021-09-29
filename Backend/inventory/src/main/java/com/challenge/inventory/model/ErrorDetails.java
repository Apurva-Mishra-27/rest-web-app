package com.challenge.inventory.model;

import java.util.Date;

public class ErrorDetails {
 Date timeStamp;
 String errorCode;
 String errorMessage;
 public ErrorDetails() {
	 
 }
public ErrorDetails(Date timeStamp, String errorCode, String errorMessage) {
	super();
	this.timeStamp = timeStamp;
	this.errorCode = errorCode;
	this.errorMessage = errorMessage;
}
public Date getTimeStamp() {
	return timeStamp;
}
public void setTimeStamp(Date timeStamp) {
	this.timeStamp = timeStamp;
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
 
}
