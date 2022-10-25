package com.bebetto.financemanager.dao;

import java.util.List;

import com.bebetto.financemanager.pojo.Expense;

public interface ExpensesDao {

	int createExpense(final Expense expense);

	boolean deleteExpense(final int expenseId);

	Expense getExpense(final int expenseId);

	List<Expense> getExpenses();

	boolean updateExpense(final Expense expense);

}
