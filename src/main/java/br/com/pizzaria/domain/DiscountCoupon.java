package br.com.pizzaria.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DiscountCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String description;
	
	@Column(name = "expiration_date", nullable = false)
	private Calendar expirationDate;
	
	@Column(name = "percentage_discount", nullable = false)
	private int percentageDiscount;
	
	@Column(name = "amount_used", nullable = false)
	private long amountUsed;
	
	@Column(nullable = false)
	private String code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(int percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

	public long getAmountUsed() {
		return amountUsed;
	}

	public void setAmountUsed(long amountUsed) {
		this.amountUsed = amountUsed;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
