package com.bebetto.financemanager.expense;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bebetto.financemanager.validator.DateConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

	private static final String VALID_DATE_FORMAT = "yyyy-MM-dd";

	@Min(0)
	private int id;

	@Min(0)
	@JsonProperty("category-id")
	private int categoryId;

	@Size(max = 200, message = "'comment' cannot have more than 200 characters...!")
	private String comment;

	@DateConstraint(pattern = VALID_DATE_FORMAT, dateRangeValidate = true, minDate = 100L, maxDate = 100L)
	private String date;

	@NotNull(message = "'amount' cannot be null...!")
	private BigDecimal amount;

	public Expense() {
		super();
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public String getComment() {
		return this.comment;
	}

	public String getDate() {
		return this.date;
	}

	public int getId() {
		return this.id;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public void setCategoryId(final int categoryId) {
		this.categoryId = categoryId;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Expense [getAmount()=" + getAmount() + ", getCategoryId()=" + getCategoryId() + ", getComment()="
				+ getComment() + ", getDate()=" + getDate() + ", getId()=" + getId() + "]";
	}

}
