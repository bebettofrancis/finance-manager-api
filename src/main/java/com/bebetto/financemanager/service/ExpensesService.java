package com.bebetto.financemanager.service;

import java.util.List;

import com.bebetto.financemanager.pojo.Expense;

public interface ExpensesService {

	int createExpense(final Expense expense);

	void deleteExpense(final int expenseId);

	Expense getExpense(final int expenseId);

	List<Expense> getExpenses();

	void updateExpense(final Expense expense);

}
