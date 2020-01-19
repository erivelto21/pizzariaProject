package br.com.pizzaria.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
	
	SALGADA("Salgada"), 
	DOCE("Doce"), 
	VEGETARIANA("Vegetariana");
	
	private String value;
	
	private Type(String value) {
		this.value = value;
	}
	
	@JsonValue
	public String getValue() {
		return this.value;
	}
}
