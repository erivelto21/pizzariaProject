package br.com.pizzaria.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Dough dough;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Size size;
	
	@Column(name="pizza_edge", nullable = false)
	@Enumerated(EnumType.STRING)
	private PizzaEdge pizzaEdge;
	
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

	public Dough getDough() {
		return dough;
	}

	public void setDough(Dough dough) {
		this.dough = dough;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public PizzaEdge getPizzaEdge() {
		return pizzaEdge;
	}

	public void setPizzaEdge(PizzaEdge pizzaEdge) {
		this.pizzaEdge = pizzaEdge;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
