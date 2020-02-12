package br.com.pizzaria.domain.pagarme;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionsPagarMe {
	
	@JsonProperty("api_key")
	private String apiKey;
	
	private String amount;
	
	@JsonProperty("card_number")
	private String cardNumber;
	
	@JsonProperty("card_cvv")
	private String cardCvv;
	
	@JsonProperty("card_expiration_date")
	private String cardExpirationDate;
	
	@JsonProperty("card_holder_name")
	private String cardHolderName;
	
	private Customer customer;
	private Billing billing;
	private List<Item>  items;
	
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardCvv() {
		return cardCvv;
	}
	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}
	public String getCardExpirationDate() {
		return cardExpirationDate;
	}
	public void setCardExpirationDate(String cardExpirationDate) {
		this.cardExpirationDate = cardExpirationDate;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Billing getBilling() {
		return billing;
	}
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
