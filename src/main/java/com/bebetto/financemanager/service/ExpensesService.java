package com.bebetto.financemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebetto.financemanager.dao.ExpensesDao;
import com.bebetto.financemanager.pojo.Expense;

@Service
public class ExpensesService {

	private final ExpensesDao expensesDao;

	@Autowired
	public ExpensesService(final ExpensesDao expensesDao) {
		this.expensesDao = expensesDao;
	}

	public Expense getExpense() {
		final Expense expense = new Expense(1);
		return expense;
	}

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
