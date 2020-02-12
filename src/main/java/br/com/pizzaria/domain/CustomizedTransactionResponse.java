package br.com.pizzaria.domain;

public class CustomizedTransactionResponse {

	private String message;
	private String statusValue;
	
	public CustomizedTransactionResponse(String message, String status) {
		this.message = message;
		this.statusValue = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
}
