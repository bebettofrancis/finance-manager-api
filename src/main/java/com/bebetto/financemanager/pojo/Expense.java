package com.bebetto.financemanager.pojo;

public class Expense {

	private int id;
	private int categoryId;
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

}
