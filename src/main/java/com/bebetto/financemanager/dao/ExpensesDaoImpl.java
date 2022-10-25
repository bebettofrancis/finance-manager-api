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

import com.bebetto.financemanager.pojo.Expense;

@Repository
public class ExpensesDaoImpl implements ExpensesDao {

	private static final String INSERT_EXPENSE = "INSERT INTO EXPENSE (CATEGORY_ID,COMMENT) VALUES (:categoryId,:comment)";
	private static final String GET_EXPENSE = "SELECT ID,CATEGORY_ID,COMMENT FROM EXPENSE WHERE ID=:expenseId";
	private static final String GET_EXPENSES = "SELECT ID,CATEGORY_ID,COMMENT FROM EXPENSE";
	private static final String DELETE_EXPENSE = "DELETE FROM EXPENSE WHERE ID=:expenseId";
	private static final String UPDATE_EXPENSE = "UPDATE EXPENSE SET CATEGORY_ID=:categoryId,COMMENT=:comment WHERE ID=:expenseId";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public ExpensesDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int createExpense(final Expense expense) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("categoryId", expense.getCategoryId()).addValue("comment", expense.getComment());
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(INSERT_EXPENSE, sqlParameterSource, keyHolder);
		return (int) keyHolder.getKey();
	}

	@Override
	public boolean deleteExpense(final int expenseId) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("expenseId", expenseId);
		final int deletedRows = this.namedParameterJdbcTemplate.update(DELETE_EXPENSE, sqlParameterSource);
		return deletedRows > 0;
	}

	private Expense generateExpenseFromResultSet(final ResultSet rs) throws SQLException {
		final Expense expense = new Expense();
		expense.setId(rs.getInt("ID"));
		expense.setCategoryId(rs.getInt("CATEGORY_ID"));
		expense.setComment(rs.getString("COMMENT"));
		return expense;
	}

	@Override
	public Expense getExpense(final int expenseId) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("expenseId", expenseId);
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

	@Override
	public boolean updateExpense(final Expense expense) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("categoryId", expense.getCategoryId()).addValue("comment", expense.getComment())
				.addValue("expenseId", expense.getId());
		final int updatedRows = this.namedParameterJdbcTemplate.update(UPDATE_EXPENSE, sqlParameterSource);
		return updatedRows > 0;
	}

}
