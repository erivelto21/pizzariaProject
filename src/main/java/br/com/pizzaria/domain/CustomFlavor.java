package br.com.pizzaria.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "custom_flavor")
public class CustomFlavor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private String image;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="custom_ingredient_custom_flavor",
    joinColumns={@JoinColumn(name="custom_flavor_id", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="custom_ingredient_id", referencedColumnName="id")})
	private List<CustomIngredient> ingredients;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<CustomIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<CustomIngredient> ingredients) {
		this.ingredients = ingredients;
	}
}
