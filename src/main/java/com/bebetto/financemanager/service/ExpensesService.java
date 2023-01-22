package com.bebetto.financemanager.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;

import com.bebetto.financemanager.pojo.Expense;
import com.bebetto.financemanager.pojo.ExpenseCategory;

public interface ExpensesService {

	int createExpense(final Expense expense);

	Map<String, List<Integer>> deleteCreateUpdateExpenses(final Map<String, List<Expense>> expenses);

	void deleteExpense(final int expenseId);

	Expense getExpense(final int expenseId);

	List<ExpenseCategory> getExpenseCategories();

	List<Expense> getExpenses();

	ByteArrayResource getExpensesToExport() throws IOException;

	void updateExpense(final Expense expense);

}
