package br.com.pizzaria.domain;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
	
	private String message;
	private String description;
	private HttpStatus httpStatus;
	
	public ErrorMessage(String message, String description, HttpStatus httpStatus) {
		this.message = message;
		this.description = description;
		this.httpStatus = httpStatus;
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

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
