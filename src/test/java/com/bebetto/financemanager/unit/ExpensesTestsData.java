package com.bebetto.financemanager.unit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bebetto.financemanager.pojo.Expense;
import com.bebetto.financemanager.property.ExpensesTestDataLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class ExpensesTestsData {

	private final ObjectMapper objectMapper;
	private final ExpensesTestDataLoader expensesTestDataLoader;

	@Autowired
	public ExpensesTestsData(final ObjectMapper objectMapper, final ExpensesTestDataLoader expensesTestDataLoader) {
		this.objectMapper = objectMapper;
		this.expensesTestDataLoader = expensesTestDataLoader;
	}

	public Expense createExpense_Expense_HttpStatusCreated() throws JsonProcessingException, IllegalArgumentException {
		final JsonNode expenseJsonNode = this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("createExpense_Expense_HttpStatusCreated");
		return this.objectMapper.treeToValue(expenseJsonNode, Expense.class);
	}

	public Expense deleteExpense_ExpenseId_HttpStatusNoContent()
			throws JsonProcessingException, IllegalArgumentException {
		final JsonNode expenseJsonNode = this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("deleteExpense_ExpenseId_HttpStatusNoContent");
		return this.objectMapper.treeToValue(expenseJsonNode, Expense.class);
	}

	public Expense getExpense_ExpenseId_HttpStatusNotFound() {
		return null;
	}

	public Expense getExpense_ExpenseId_HttpStatusOk() throws JsonProcessingException, IllegalArgumentException {
		final JsonNode expenseJsonNode = this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("getExpense_ExpenseId_HttpStatusOk");
		return this.objectMapper.treeToValue(expenseJsonNode, Expense.class);
	}

	public List<Expense> getExpenses_HttpGetRequest_HttpStatusOk()
			throws JsonProcessingException, IllegalArgumentException {
		List<Expense> expenses = null;
		final ArrayNode expensesArrayNode = (ArrayNode) this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("getExpenses_HttpGetRequest_HttpStatusOk");
		for (final JsonNode expenseJsonNode : expensesArrayNode) {
			if (expenses == null) {
				expenses = new ArrayList<>();
			}
			expenses.add(this.objectMapper.treeToValue(expenseJsonNode, Expense.class));
		}
		return expenses;
	}

	public List<Expense> getExportedExpenses_HttpGetRequest_HttpStatusOk()
			throws JsonProcessingException, IllegalArgumentException {
		List<Expense> expenses = null;
		final ArrayNode expensesArrayNode = (ArrayNode) this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("getExportedExpenses_HttpGetRequest_HttpStatusOk");
		for (final JsonNode expenseJsonNode : expensesArrayNode) {
			if (expenses == null) {
				expenses = new ArrayList<>();
			}
			expenses.add(this.objectMapper.treeToValue(expenseJsonNode, Expense.class));
		}
		return expenses;
	}

	public List<Expense> getExportedExpenses_NoExpensesFromDao_HttpStatusOk() {
		return null;
	}

	public Expense updateExpense_Expense_HttpStatusNotFound() throws JsonProcessingException, IllegalArgumentException {
		final JsonNode expenseJsonNode = this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("updateExpense_Expense_HttpStatusNotFound");
		return this.objectMapper.treeToValue(expenseJsonNode, Expense.class);
	}

	public Expense updateExpense_Expense_HttpStatusOk() throws JsonProcessingException, IllegalArgumentException {
		final JsonNode expenseJsonNode = this.expensesTestDataLoader.getExpensesTestDataNode()
				.get("updateExpense_Expense_HttpStatusOk");
		return this.objectMapper.treeToValue(expenseJsonNode, Expense.class);
	}

}
