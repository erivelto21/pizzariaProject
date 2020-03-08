package br.com.pizzaria.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Size {
	
	PEQUENA("Pequena"), MEDIA("Média"), GRANDE("Grande"), EXTRAGRANDE("Extra Grande");
	
	private String value;
	
	Size(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
