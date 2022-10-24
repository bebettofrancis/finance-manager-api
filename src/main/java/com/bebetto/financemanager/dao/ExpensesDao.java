package com.bebetto.financemanager.dao;

import java.util.List;

import com.bebetto.financemanager.pojo.Expense;

public interface ExpensesDao {

	Expense getExpense(final int expenseId);

	List<Expense> getExpenses();

}
