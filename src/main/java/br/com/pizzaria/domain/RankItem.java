package br.com.pizzaria.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "RankItemMapping", 
	entities = @EntityResult(entityClass = RankItem.class, fields = {
		@FieldResult(name = "name", column = "name"),
		@FieldResult(name = "type", column = "type"),
		@FieldResult(name = "price", column = "price"),
		@FieldResult(name = "image", column = "image"),
		@FieldResult(name = "amount", column = "amount") }))
@NamedNativeQuery(
		  name = "RankItem",
		  query = "SELECT  f.name, f.type, f.price, f.image, sum(amount) as amount FROM pizza as p JOIN custom_flavor as f ON p.custom_flavor_id = f.id group by f.name, f.type, f.price, f.image, amount order by amount desc",
		  resultSetMapping = "RankItemMapping")
@Entity
public class RankItem {

	@Id
	private String name;

	private BigDecimal price;
	
	private String image;

	@Enumerated(EnumType.STRING)
	private Type type;

	private long amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
