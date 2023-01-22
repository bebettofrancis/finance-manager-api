package com.bebetto.financemanager.dao;

import java.util.List;

import com.bebetto.financemanager.pojo.Expense;
import com.bebetto.financemanager.pojo.ExpenseCategory;

public interface ExpensesDao {

	int createExpense(final Expense expense);

	void createExpenses(final List<Expense> expenses);

	boolean deleteExpense(final int expenseId);

	List<Integer> deleteExpenses(final List<Expense> expenses);

	Expense getExpense(final int expenseId);

	List<ExpenseCategory> getExpenseCategories();

	List<Expense> getExpenses();

	boolean updateExpense(final Expense expense);

	List<Integer> updateExpenses(final List<Expense> expenses);

}
