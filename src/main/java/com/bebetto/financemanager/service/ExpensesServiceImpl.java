package com.bebetto.financemanager.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
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
import com.bebetto.financemanager.pojo.Expense;
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

	public void createExpensesSheet(final Workbook workbook, final List<Expense> expenses) {
		final Sheet sheet = workbook.createSheet("Expenses");
		final Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Sr. no");
		headerRow.createCell(1).setCellValue("Category");
		headerRow.createCell(2).setCellValue("Comment");
		headerRow.createCell(3).setCellValue("Expense date");
		headerRow.createCell(4).setCellValue("Amount");
		if (CommonUtility.isEmpty(expenses)) {
			return;
		}
		int dataRow = 1;
		for (final Expense expense : expenses) {
			final Row row = sheet.createRow(dataRow);
			row.createCell(0).setCellValue(dataRow);
			row.createCell(1).setCellValue(expense.getCategoryId());
			row.createCell(2).setCellValue(expense.getComment());
			row.createCell(3).setCellValue(expense.getDate());
			row.createCell(4).setCellValue(expense.getAmount().toString());
			++dataRow;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteExpense(final int expenseId) {
		final Expense expense = this.expensesDao.getExpense(expenseId);
		if (expense == null) {
			throw new ExpenseNotFoundException(EXPENSE_NOT_FOUND);
		}
		this.expensesDao.deleteExpense(expenseId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expense getExpense(final int expenseId) {
		return Optional.ofNullable(this.expensesDao.getExpense(expenseId))
				.orElseThrow(() -> new ExpenseNotFoundException(EXPENSE_NOT_FOUND));
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
		final Expense expenseTmp = this.expensesDao.getExpense(expense.getId());
		if (expenseTmp == null) {
			throw new ExpenseNotFoundException(EXPENSE_NOT_FOUND);
		}
		this.expensesDao.updateExpense(expense);
	}

}
