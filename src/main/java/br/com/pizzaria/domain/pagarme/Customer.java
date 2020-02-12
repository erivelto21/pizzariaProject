package br.com.pizzaria.domain.pagarme;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
	
	@JsonProperty("external_id")
	private String externalId;
	
	private String name;
	private String type;
	private String country;
	private String email;
	
	@JsonProperty("phone_numbers")
	private List<String> phoneNumber;
	
	private List<Documents> documents;
	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Documents> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Documents> documents) {
		this.documents = documents;
	}
}
