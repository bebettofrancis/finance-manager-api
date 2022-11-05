package com.bebetto.financemanager.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.bebetto.financemanager.validator.DateConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

	private static final String VALID_DATE_FORMAT = "yyyy-MM-dd";

	@Min(0)
	private int id;

	@Min(0)
	private int categoryId;

	@Size(max = 200, message = "Field length cannot be greater tha 200 characters...!")
	private String comment;

	@DateConstraint(pattern = VALID_DATE_FORMAT, dateRangeValidate = true, minDate = 100L, maxDate = 100L)
	private String date;

	public Expense() {
		super();
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
		return "Expense [getCategoryId()=" + getCategoryId() + ", getComment()=" + getComment() + ", getId()=" + getId()
				+ "]";
	}

}
