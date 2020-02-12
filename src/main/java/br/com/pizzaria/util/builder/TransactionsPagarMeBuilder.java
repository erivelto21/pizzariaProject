package br.com.pizzaria.util.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.domain.pagarme.Billing;
import br.com.pizzaria.domain.pagarme.Documents;
import br.com.pizzaria.domain.pagarme.Item;
import br.com.pizzaria.domain.pagarme.TransactionsPagarMe;

public class TransactionsPagarMeBuilder {
	
	private Customer customer;
	private String apiKey;

	public TransactionsPagarMeBuilder(Customer customer, String apiKey) {
		this.customer = customer;
		this.apiKey = apiKey;
	}
	
	public TransactionsPagarMe Builder() {
		return this.transactionsBuilder();
	}
	
	private TransactionsPagarMe transactionsBuilder(){
		TransactionsPagarMe transactionsPagarMe = new TransactionsPagarMe();
		
		transactionsPagarMe.setApiKey(apiKey);
		transactionsPagarMe.setAmount(this.getAmount(this.customer.getCart()));
		transactionsPagarMe.setCardCvv(this.customer.getCreditCard().getCardCvv() + "");
		transactionsPagarMe.setCardExpirationDate(this.customer.getCreditCard().getCardExpirationDate() + "");
		transactionsPagarMe.setCardHolderName(this.customer.getCreditCard().getCardHolderName());
		transactionsPagarMe.setCardNumber(this.customer.getCreditCard().getCardNumber() + "");
		
		br.com.pizzaria.domain.pagarme.Customer customer = this.customerBuilder(this.customer.getSystemUser());
		transactionsPagarMe.setCustomer(customer);
		
		Billing billing = this.billingBuilder(this.customer.getSystemUser().getAddress(), this.customer.getSystemUser().getFirstName());
		transactionsPagarMe.setBilling(billing);
		
		List<Item> list = this.itemsBuilder(this.customer.getCart());
		transactionsPagarMe.setItems(list);
		
		return transactionsPagarMe;
	}
	
	private Billing billingBuilder(Address a, String name) {
		Billing billing = new Billing();
		billing.setName(name);
		
		br.com.pizzaria.domain.pagarme.Address address = this.buildAddress(a);
		billing.setAddress(address);;
		
		return billing;
	}
	
	private br.com.pizzaria.domain.pagarme.Address buildAddress(Address a) {
		br.com.pizzaria.domain.pagarme.Address address = new br.com.pizzaria.domain.pagarme.Address();
		
		address.setCountry("br");
		address.setState(a.getState());
		address.setCity(a.getCity());
		address.setNeighborhood(a.getNeighborhood());
		address.setStreet(a.getStreet());
		address.setStreetNumber(a.getNumber() + "");
		address.setZipcode("60000000");//fortaleza ZipCode
		
		return address;
	}
	
	private  br.com.pizzaria.domain.pagarme.Customer customerBuilder(SystemUser user) {
		br.com.pizzaria.domain.pagarme.Customer customer = new br.com.pizzaria.domain.pagarme.Customer();
		
		customer.setExternalId(user.getId() + "");
		customer.setName(user.getFirstName() + " " + user.getLastName());
		customer.setType("individual");
		customer.setCountry("br");
		customer.setEmail(user.getEmail());
		
		List<String> phoneList = new ArrayList<String>();
		phoneList.add(preparePhone("+" + user.getPhone()));
		customer.setPhoneNumber(phoneList);
		
		List<Documents> documents = this.buildDocuments();
		customer.setDocuments(documents);

		return customer;
	}
	
	private List<Documents> buildDocuments() {
		Documents document = new Documents("cpf", "99999999999");
		List<Documents> documents = new ArrayList<Documents>();
		documents.add(document);
		
		return documents;
	}
	
	private List<Item> itemsBuilder(List<Pizza> orderedPizzas) {
		List<Item> list = new ArrayList<Item>();
		int i = 1;
		for(Pizza o: orderedPizzas) {
			list.add(new Item(i + "" , "Pizza de " + o.getFlavor().getName(), o.getAmount(), o.getFlavor().getPrice().intValue()));
			i++;
		}
		
		return list;
	}
	
	private String removeDot(BigDecimal amount) {
		return amount.toString().replace(".", "");
	}

	private String preparePhone(String phone) {
		phone = phone.replace("(", "");
		phone = phone.replace(")", "");
		phone = phone.replace("-", "");
		phone = phone.replace(" ", "");
		return phone;
	}
	
	private String getAmount(List<Pizza> list) {
		BigDecimal amount = new BigDecimal("00.00");

		for(Pizza orderedPizza: list) {
			int aux = orderedPizza.getAmount();
			amount = amount.add(orderedPizza.getFlavor().getPrice().multiply(new BigDecimal(aux)));
		}

		return this.removeDot(amount);
	}
}
