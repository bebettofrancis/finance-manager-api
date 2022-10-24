package com.bebetto.financemanager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bebetto.financemanager.pojo.Expense;

@Repository
public class ExpensesDaoImpl implements ExpensesDao {

	@Override
	public Expense getExpense(final int expenseId) {
		return new Expense(1);
	}

	@Override
	public List<Expense> getExpenses() {
		final List<Expense> expenses = new ArrayList<>();
		expenses.add(new Expense(1));
		expenses.add(new Expense(2));
		expenses.add(new Expense(3));
		expenses.add(new Expense(4));
		expenses.add(new Expense(5));
		expenses.add(new Expense(6));
		return expenses;
	}

}
