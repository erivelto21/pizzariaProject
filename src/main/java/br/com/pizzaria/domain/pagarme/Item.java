package br.com.pizzaria.domain.pagarme;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
	
	private String id;
	private String title;
	
	@JsonProperty("quantity")
	private int amount;
	
	@JsonProperty("unit_price")
	private int unitPrice;
	
	private boolean tangible;

	public Item(String id, String flavor, int amount, int unitPrice) {
		this.id = id;
		this.title = flavor;
		this.amount = amount;
		this.unitPrice = unitPrice;
		this.tangible = true; //always true because we sell food
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public boolean isTangible() {
		return tangible;
	}

	public void setTangible(boolean tangible) {
		this.tangible = tangible;
	}
}
