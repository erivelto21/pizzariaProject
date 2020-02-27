package br.com.pizzaria.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pizza {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="custom_flavor_id", nullable=false)
	private CustomFlavor customFlavor;
	
	@Column(nullable = false)
	private int amount;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CustomFlavor getCustomFlavor() {
		return customFlavor;
	}

	public void setCustomFlavor(CustomFlavor customFlavor) {
		this.customFlavor = customFlavor;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
