package com.bebetto.financemanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bebetto.financemanager.logger.LoggingManager;
import com.bebetto.financemanager.pojo.Expense;
import com.bebetto.financemanager.pojo.ExpenseCategory;

@Repository
public class ExpensesDaoImpl implements ExpensesDao {

	private static final String EXPENSE_ID_PARAM = "expenseId";
	private static final String CATEGORY_ID_PARAM = "categoryId";
	private static final String COMMENT_PARAM = "comment";
	private static final String AMOUNT_PARAM = "amount";
	private static final String EXPENSE_DATE_PARAM = "expenseDate";

	private static final String INSERT_EXPENSE = "INSERT INTO EXPENSE (CATEGORY_ID,COMMENT,AMOUNT,EXPENSE_DATE) VALUES\n" //
			+ "(:" + CATEGORY_ID_PARAM + ",:" + COMMENT_PARAM + ",:" + AMOUNT_PARAM + ",:" + EXPENSE_DATE_PARAM + ")";
	private static final String GET_EXPENSES = "SELECT ID,CATEGORY_ID,COMMENT,AMOUNT,EXPENSE_DATE FROM EXPENSE";
	private static final String GET_EXPENSE_CATEGORIES = "SELECT ID,NAME FROM EXPENSE_CATEGORY";
	private static final String GET_EXPENSE = GET_EXPENSES + " WHERE ID=:" + EXPENSE_ID_PARAM;
	private static final String DELETE_EXPENSE = "DELETE FROM EXPENSE WHERE ID=:" + EXPENSE_ID_PARAM;
	private static final String UPDATE_EXPENSE = "UPDATE EXPENSE SET CATEGORY_ID=:" + CATEGORY_ID_PARAM + ",\n" //
			+ "COMMENT=:" + COMMENT_PARAM + ",AMOUNT=:" + AMOUNT_PARAM + ",EXPENSE_DATE=:" + EXPENSE_DATE_PARAM + "\n" //
			+ "WHERE ID=:" + EXPENSE_ID_PARAM;

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public ExpensesDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int createExpense(final Expense expense) {
		final SqlParameterSource sqlParameterSource = getSqlParameterSourceForCreatingExpense(expense);
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(INSERT_EXPENSE, sqlParameterSource, keyHolder);
		return (int) keyHolder.getKey();
	}

	@Override
	public void createExpenses(final List<Expense> expenses) {
		final SqlParameterSource[] sqlParameterSource = expenses.stream()
				.map(this::getSqlParameterSourceForCreatingExpense).toArray(MapSqlParameterSource[]::new);
		this.namedParameterJdbcTemplate.batchUpdate(INSERT_EXPENSE, sqlParameterSource);
	}

	@Override
	public boolean deleteExpense(final int expenseId) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue(EXPENSE_ID_PARAM, expenseId);
		final int deletedRows = this.namedParameterJdbcTemplate.update(DELETE_EXPENSE, sqlParameterSource);
		return deletedRows > 0;
	}

	@Override
	public List<Integer> deleteExpenses(final List<Expense> expenses) {
		final SqlParameterSource[] sqlParameterSource = expenses.stream()
				.map(expense -> new MapSqlParameterSource().addValue(EXPENSE_ID_PARAM, expense.getId()))
				.toArray(MapSqlParameterSource[]::new);
		/*
		 * Using a batch delete instead of an SQL 'IN' operator to delete multiple
		 * expenses in order to identify the expenses that have not been deleted.
		 */
		final int[] deletedExpenses = this.namedParameterJdbcTemplate.batchUpdate(DELETE_EXPENSE, sqlParameterSource);
		final List<Integer> notDeletedExpenses = new ArrayList<>();
		for (int i = 0, j = deletedExpenses.length; i < j; ++i) {
			if (deletedExpenses[i] == 0) {
				notDeletedExpenses.add(expenses.get(i).getId());
			}
		}
		LoggingManager.warn("Not deleted expense ids: " + notDeletedExpenses);
		return notDeletedExpenses;
	}

	private ExpenseCategory generateExpenseCategoryFromResultSet(final ResultSet rs) throws SQLException {
		final ExpenseCategory expenseCategory = new ExpenseCategory();
		expenseCategory.setId(rs.getInt("ID"));
		expenseCategory.setName(rs.getString("NAME"));
		return expenseCategory;
	}

	private Expense generateExpenseFromResultSet(final ResultSet rs) throws SQLException {
		final Expense expense = new Expense();
		expense.setId(rs.getInt("ID"));
		expense.setCategoryId(rs.getInt("CATEGORY_ID"));
		expense.setComment(rs.getString("COMMENT"));
		expense.setAmount(rs.getBigDecimal("AMOUNT"));
		expense.setDate(rs.getString("EXPENSE_DATE"));
		return expense;
	}

	@Override
	public Expense getExpense(final int expenseId) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue(EXPENSE_ID_PARAM, expenseId);
		return this.namedParameterJdbcTemplate.query(GET_EXPENSE, sqlParameterSource,
				(ResultSetExtractor<Expense>) rs -> {
					Expense expense = null;
					while (rs.next()) {
						if (expense == null) {
							expense = generateExpenseFromResultSet(rs);
						}
					}
					return expense;
				});
	}

	@Override
	public List<ExpenseCategory> getExpenseCategories() {
		return this.namedParameterJdbcTemplate.query(GET_EXPENSE_CATEGORIES,
				(ResultSetExtractor<List<ExpenseCategory>>) rs -> {
					List<ExpenseCategory> expenseCategories = null;
					while (rs.next()) {
						if (expenseCategories == null) {
							expenseCategories = new ArrayList<>();
						}
						final ExpenseCategory expenseCategory = generateExpenseCategoryFromResultSet(rs);
						expenseCategories.add(expenseCategory);
					}
					return expenseCategories;
				});
	}

	@Override
	public List<Expense> getExpenses() {
		return this.namedParameterJdbcTemplate.query(GET_EXPENSES, (ResultSetExtractor<List<Expense>>) rs -> {
			List<Expense> expenses = null;
			while (rs.next()) {
				if (expenses == null) {
					expenses = new ArrayList<>();
				}
				final Expense expense = generateExpenseFromResultSet(rs);
				expenses.add(expense);
			}
			return expenses;
		});
	}

	private SqlParameterSource getSqlParameterSourceForCreatingExpense(final Expense expense) {
		return new MapSqlParameterSource().addValue(CATEGORY_ID_PARAM, expense.getCategoryId())
				.addValue(COMMENT_PARAM, expense.getComment()).addValue(AMOUNT_PARAM, expense.getAmount())
				.addValue(EXPENSE_DATE_PARAM, expense.getDate());
	}

	private SqlParameterSource getSqlParameterSourceForUpdatingExpense(final Expense expense) {
		return new MapSqlParameterSource().addValue(EXPENSE_ID_PARAM, expense.getId())
				.addValue(CATEGORY_ID_PARAM, expense.getCategoryId()).addValue(COMMENT_PARAM, expense.getComment())
				.addValue(AMOUNT_PARAM, expense.getAmount()).addValue(EXPENSE_DATE_PARAM, expense.getDate());
	}

	@Override
	public boolean updateExpense(final Expense expense) {
		final SqlParameterSource sqlParameterSource = getSqlParameterSourceForUpdatingExpense(expense);
		final int updatedRows = this.namedParameterJdbcTemplate.update(UPDATE_EXPENSE, sqlParameterSource);
		return updatedRows > 0;
	}

	@Override
	public List<Integer> updateExpenses(final List<Expense> expenses) {
		final SqlParameterSource[] sqlParameterSource = expenses.stream()
				.map(this::getSqlParameterSourceForUpdatingExpense).toArray(MapSqlParameterSource[]::new);
		final int[] updatedExpenses = this.namedParameterJdbcTemplate.batchUpdate(UPDATE_EXPENSE, sqlParameterSource);
		final List<Integer> notUpdatedExpenses = new ArrayList<>();
		for (int i = 0, j = updatedExpenses.length; i < j; ++i) {
			if (updatedExpenses[i] == 0) {
				notUpdatedExpenses.add(expenses.get(i).getId());
			}
		}
		LoggingManager.warn("Not updated expense ids: " + notUpdatedExpenses);
		return notUpdatedExpenses;
	}

}
