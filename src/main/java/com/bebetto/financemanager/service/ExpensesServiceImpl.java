package com.bebetto.financemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bebetto.financemanager.dao.ExpensesDao;
import com.bebetto.financemanager.exception.ExpenseNotFoundException;
import com.bebetto.financemanager.pojo.Expense;

@Service
public class ExpensesServiceImpl implements ExpensesService {

	private final ExpensesDao expensesDao;

	@Autowired
	public ExpensesServiceImpl(final ExpensesDao expensesDao) {
		this.expensesDao = expensesDao;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expense getExpense(final int expenseId) {
		final Expense expense = this.expensesDao.getExpense(expenseId);
		if (expense == null) {
			throw new ExpenseNotFoundException("Expense not found...!");
		}
		return expense;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expense> getExpenses() {
		return this.expensesDao.getExpenses();
	}

}
