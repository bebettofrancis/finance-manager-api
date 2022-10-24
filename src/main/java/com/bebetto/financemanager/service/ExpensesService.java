package com.bebetto.financemanager.service;

import java.util.List;

import com.bebetto.financemanager.pojo.Expense;

public interface ExpensesService {

	Expense getExpense(final int expenseId);

	List<Expense> getExpenses();

}
