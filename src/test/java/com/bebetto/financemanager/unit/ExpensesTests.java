package com.bebetto.financemanager.unit;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bebetto.financemanager.dao.ExpensesDao;
import com.bebetto.financemanager.pojo.Expense;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class ExpensesTests {

	private final MockMvc mockMvc;
	private final ExpensesTestsData expensesTestsData;
	private final ObjectMapper objectMapper;

	@MockBean
	private ExpensesDao expensesDao;

	@Autowired
	public ExpensesTests(final MockMvc mockMvc, final ExpensesTestsData expensesTestsData,
			final ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.expensesTestsData = expensesTestsData;
		this.objectMapper = objectMapper;
	}

	@Test
	void createExpense_Expense_HttpStatusCreated() throws Exception {
		final Expense expense = this.expensesTestsData.createExpense_Expense_HttpStatusCreated();
		Mockito.when(this.expensesDao.createExpense(any(Expense.class))).thenReturn(1);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/expenses")
						.content(this.objectMapper.writeValueAsString(expense)).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content()
						.string(Matchers.containsString("Request processed successfully")));
	}

	@Test
	void deleteExpense_ExpenseId_HttpStatusNoContent() throws Exception {
		final Expense expense = this.expensesTestsData.deleteExpense_ExpenseId_HttpStatusNoContent();
		Mockito.when(this.expensesDao.deleteExpense(any(Integer.class))).thenReturn(true);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/expenses/{expenseId}", expense.getId()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	void deleteExpense_ExpenseId_HttpStatusNotFound() throws Exception {
		Mockito.when(this.expensesDao.deleteExpense(any(Integer.class))).thenReturn(false);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/expenses/{expenseId}", 1))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Expense not found")));
	}

	@Test
	void getExpense_ExpenseId_HttpStatusNotFound() throws Exception {
		final Expense expense = this.expensesTestsData.getExpense_ExpenseId_HttpStatusNotFound();
		Mockito.when(this.expensesDao.getExpense(any(Integer.class))).thenReturn(expense);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses/{expenseId}", 1))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Expense not found")));
	}

	@Test
	void getExpense_ExpenseId_HttpStatusOk() throws Exception {
		final Expense expense = this.expensesTestsData.getExpense_ExpenseId_HttpStatusOk();
		Mockito.when(this.expensesDao.getExpense(any(Integer.class))).thenReturn(expense);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses/{expenseId}", expense.getId()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content()
						.string(Matchers.containsString("Request processed successfully")));
	}

	@Test
	void getExpenses_HttpGetRequest_HttpStatusOk() throws Exception {
		final List<Expense> expenses = this.expensesTestsData.getExpenses_HttpGetRequest_HttpStatusOk();
		Assertions.assertThat(expenses).isNotEmpty();
		Mockito.when(this.expensesDao.getExpenses()).thenReturn(expenses);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content()
						.string(Matchers.containsString("Request processed successfully")));
	}

	@Test
	void getExportedExpenses_HttpGetRequest_HttpStatusOk() throws Exception {
		final List<Expense> expenses = this.expensesTestsData.getExportedExpenses_HttpGetRequest_HttpStatusOk();
		Assertions.assertThat(expenses).isNotEmpty();
		Mockito.when(this.expensesDao.getExpenses()).thenReturn(expenses);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses/export"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM));
	}

	@Test
	void getExportedExpenses_NoExpensesFromDao_HttpStatusOk() throws Exception {
		final List<Expense> expenses = this.expensesTestsData.getExportedExpenses_NoExpensesFromDao_HttpStatusOk();
		Assertions.assertThat(expenses).isNull();
		Mockito.when(this.expensesDao.getExpenses()).thenReturn(expenses);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses/export"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM));
	}

	@Test
	void updateExpense_Expense_HttpStatusNoContent() throws Exception {
		final Expense expense = this.expensesTestsData.updateExpense_Expense_HttpStatusOk();
		Mockito.when(this.expensesDao.updateExpense(any(Expense.class))).thenReturn(true);
		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/api/v1/expenses")
						.content(this.objectMapper.writeValueAsString(expense)).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	void updateExpense_Expense_HttpStatusNotFound() throws Exception {
		final Expense expense = this.expensesTestsData.updateExpense_Expense_HttpStatusNotFound();
		Mockito.when(this.expensesDao.updateExpense(any(Expense.class))).thenReturn(false);
		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/api/v1/expenses")
						.content(this.objectMapper.writeValueAsString(expense)).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Expense not found")));
	}

}
