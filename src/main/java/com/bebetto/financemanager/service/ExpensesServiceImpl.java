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
	public int createExpense(final Expense expense) {
		return this.expensesDao.createExpense(expense);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteExpense(final int expenseId) {
		final Expense expense = this.expensesDao.getExpense(expenseId);
		if (expense == null) {
			throw new ExpenseNotFoundException("Expense not found...!");
		}
		this.expensesDao.deleteExpense(expenseId);
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateExpense(final Expense expense) {
		final Expense expenseDb = this.expensesDao.getExpense(expense.getId());
		if (expenseDb == null) {
			throw new ExpenseNotFoundException("Expense not found...!");
		}
		this.expensesDao.updateExpense(expense);
	}

}
