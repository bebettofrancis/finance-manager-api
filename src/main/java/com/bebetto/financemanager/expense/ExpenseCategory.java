package com.bebetto.financemanager.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseCategory {

	private int id;
	private String name;

	public ExpenseCategory() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
