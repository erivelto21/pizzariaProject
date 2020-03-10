package br.com.pizzaria.domain;

import java.math.BigDecimal;

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
	
	@Column(name = "additionals_value", nullable = false)
	private BigDecimal additionalsValue;
	
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

	public BigDecimal getAdditionalsValue() {
		return additionalsValue;
	}

	public void setAdditionalsValue(BigDecimal additionalsValue) {
		this.additionalsValue = additionalsValue;
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
	
	public BigDecimal calculatePrizePerSize() {
		BigDecimal valueAdditionalPerSize = null;
		BigDecimal value = this.customFlavor.getPrice();

		switch(this.size) {
			case PEQUENA:
				valueAdditionalPerSize = new BigDecimal("-02.00");
				break;
			case GRANDE:
				valueAdditionalPerSize = new BigDecimal("02.00");
				break;
			case EXTRAGRANDE:
				valueAdditionalPerSize = new BigDecimal("04.00");
				break;
			default:
				return value;
		}

		value = value.add(valueAdditionalPerSize);

		return value;
	}
	
	public void calculateAdditionals() {
		this.additionalsValue = new BigDecimal("00.00");
		
		this.additionalsValue = this.calculateAdditionalsOfDough().add(this.additionalsValue);
		this.additionalsValue = this.calculateAdditionalsOfPizzaEdge().add(this.additionalsValue);
		this.additionalsValue = this.calculateAdditionalsOfIngredients().add(this.additionalsValue);
	}
	
	private BigDecimal calculateAdditionalsOfDough() {
		return this.dough == Dough.TRADICIONAL ? new BigDecimal("00.00") : new BigDecimal("01.00"); 
	}
	
	private BigDecimal calculateAdditionalsOfPizzaEdge() {
		return this.pizzaEdge == PizzaEdge.SEMRECHEIO ? new BigDecimal("00.00") : new BigDecimal("01.00"); 
	}
	
	private BigDecimal calculateAdditionalsOfIngredients() {
		BigDecimal total = new BigDecimal("00.00");
		
		for(CustomIngredient i : this.customFlavor.getIngredients()) {
			total = total.add(this.calculateIngredientAdditional(i));
		}

		return total;
	}
	
	private BigDecimal calculateIngredientAdditional(CustomIngredient customIngredient) {
		int aux = (customIngredient.getAmount() - 1) * 1;
		return new BigDecimal(aux);
	}
}
