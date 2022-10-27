package com.bebetto.financemanager.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Expense {

	@Min(0)
	private int id;

	@Min(0)
	private int categoryId;

	@Size(max = 200, message = "Field length cannot be greater tha 200 characters...!")
	private String comment;

	public Expense() {
		super();
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public String getComment() {
		return this.comment;
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

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Expense [getCategoryId()=" + getCategoryId() + ", getComment()=" + getComment() + ", getId()=" + getId()
				+ "]";
	}

}
