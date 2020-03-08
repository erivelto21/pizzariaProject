package br.com.pizzaria.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Dough {

	TRADICIONAL("Tradicional"), INTEGRAL("Integral"), SICILIANA("Siciliana"), NOVAIORQUINA("Nova-Iorquina");

	private String value;

	Dough(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
