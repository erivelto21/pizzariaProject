package br.com.pizzaria.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PizzaEdge {

	SEMRECHEIO("Sem recheio"), CATUPIRY("Catupiry"), CHEDDAR("Cheddar"), CHOCOLATEAOLEITE("Chocolate ao leite");

	private String value;

	PizzaEdge(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
