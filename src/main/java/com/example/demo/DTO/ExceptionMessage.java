package com.example.demo.DTO;


public class ExceptionMessage {

	private String status;
	private String message;
	private String description;
	
	
	public ExceptionMessage(String status, String message, String description) {
		super();
		this.status = status;
		this.message = message;
		this.description = description;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
