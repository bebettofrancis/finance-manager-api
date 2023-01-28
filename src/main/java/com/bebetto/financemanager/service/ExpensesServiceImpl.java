package com.bebetto.financemanager.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bebetto.financemanager.dao.ExpensesDao;
import com.bebetto.financemanager.exception.ExpenseNotFoundException;
import com.bebetto.financemanager.expense.Expense;
import com.bebetto.financemanager.expense.ExpenseCategory;
import com.bebetto.financemanager.utility.CommonUtility;
import com.bebetto.financemanager.utility.ExcelFileUtility;

@Service
public class ExpensesServiceImpl implements ExpensesService {

	private static final String EXPENSE_NOT_FOUND = "Expense not found...!";

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

	private void createExpensesSheet(final Workbook workbook, final List<Expense> expenses) {
		final Sheet sheet = workbook.createSheet("Expenses");
		createExpensesSheetHeaderRow(sheet);
		if (CommonUtility.isEmpty(expenses)) {
			return;
		}
		int dataRow = 1;
		for (final Expense expense : expenses) {
			createExpensesSheetBodyRow(sheet, expense, dataRow);
			++dataRow;
		}
	}

	private void createExpensesSheetBodyRow(final Sheet sheet, final Expense expense, final int index) {
		final Row row = sheet.createRow(index);
		row.createCell(0).setCellValue(index);
		row.createCell(1).setCellValue(expense.getCategoryId());
		row.createCell(2).setCellValue(expense.getComment());
		row.createCell(3).setCellValue(expense.getDate());
		row.createCell(4).setCellValue(expense.getAmount().toString());
	}

	private void createExpensesSheetHeaderRow(final Sheet sheet) {
		final Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Sr. no");
		headerRow.createCell(1).setCellValue("Category");
		headerRow.createCell(2).setCellValue("Comment");
		headerRow.createCell(3).setCellValue("Expense date");
		headerRow.createCell(4).setCellValue("Amount");
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Map<String, List<Integer>> deleteCreateUpdateExpenses(final Map<String, List<Expense>> expenses) {
		if (CommonUtility.isEmpty(expenses)) {
			return Collections.emptyMap();
		}
		final Map<String, List<Integer>> nonDeletedUpdatedExpenses = new HashMap<>();
		final List<Expense> toBeDeletedExpenses = expenses.get("delete");
		if (!CommonUtility.isEmpty(toBeDeletedExpenses)) {
			final List<Integer> deletedExpenses = this.expensesDao.deleteExpenses(toBeDeletedExpenses);
			nonDeletedUpdatedExpenses.put("not-deleted", deletedExpenses);
		}
		final List<Expense> toBeUpdatedExpenses = expenses.get("update");
		if (!CommonUtility.isEmpty(toBeUpdatedExpenses)) {
			final List<Integer> updatedExpenses = this.expensesDao.updateExpenses(toBeUpdatedExpenses);
			nonDeletedUpdatedExpenses.put("not-updated", updatedExpenses);
		}
		final List<Expense> toBeCreatedExpenses = expenses.get("insert");
		if (!CommonUtility.isEmpty(toBeCreatedExpenses)) {
			this.expensesDao.createExpenses(toBeCreatedExpenses);
		}
		return nonDeletedUpdatedExpenses;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteExpense(final int expenseId) {
		if (!this.expensesDao.deleteExpense(expenseId)) {
			throw new ExpenseNotFoundException(EXPENSE_NOT_FOUND);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expense getExpense(final int expenseId) {
		return Optional.ofNullable(this.expensesDao.getExpense(expenseId))
				.orElseThrow(() -> new ExpenseNotFoundException(EXPENSE_NOT_FOUND));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpenseCategory> getExpenseCategories() {
		return this.expensesDao.getExpenseCategories();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expense> getExpenses() {
		return this.expensesDao.getExpenses();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ByteArrayResource getExpensesToExport() throws IOException {
		final ExcelFileUtility excelFileUtility = new ExcelFileUtility.ExcelFileUtilityBuilder().build();
		try (final Workbook workbook = excelFileUtility.getWorkbook();
				final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
			final List<Expense> expenses = this.expensesDao.getExpenses();
			createExpensesSheet(workbook, expenses);
			workbook.write(byteArrayOutputStream);
			return new ByteArrayResource(byteArrayOutputStream.toByteArray()) {
				@Override
				public String getFilename() {
					return excelFileUtility.getFileName();
				}
			};
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateExpense(final Expense expense) {
		if (!this.expensesDao.updateExpense(expense)) {
			throw new ExpenseNotFoundException(EXPENSE_NOT_FOUND);
		}
	}

}
