package com.bebetto.financemanager.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;

import com.bebetto.financemanager.pojo.Expense;

public interface ExpensesService {

	int createExpense(final Expense expense);

	void deleteExpense(final int expenseId);

	Expense getExpense(final int expenseId);

	List<Expense> getExpenses();

	ByteArrayResource getExpensesToExport() throws IOException;

	void updateExpense(final Expense expense);

}
