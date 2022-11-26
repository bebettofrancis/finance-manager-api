package com.bebetto.financemanager.unit;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	private final ObjectMapper objectMapper;

	@MockBean
	private ExpensesDao expensesDao;

	@Autowired
	public ExpensesTests(final MockMvc mockMvc) {
		this.mockMvc = mockMvc;
		this.objectMapper = new ObjectMapper();
	}

	@Test
	void createExpense_Expense_HttpStatusCreated() throws Exception {
		final Expense expense = getDummyExpense();
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
		final Expense expense = getDummyExpense();
		Mockito.when(this.expensesDao.getExpense(any(Integer.class))).thenReturn(expense);
		Mockito.when(this.expensesDao.deleteExpense(any(Integer.class))).thenReturn(true);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/expenses/{expenseId}", expense.getId()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
	}

	private Expense getDummyExpense() {
		final Expense expense = new Expense();
		expense.setId(1);
		expense.setCategoryId(3);
		expense.setComment("test");
		expense.setAmount(new BigDecimal(567.34));
		expense.setDate("2022-09-13");
		return expense;
	}

	@Test
	void getExpense_ExpenseId_HttpStatusOk() throws Exception {
		final Expense expense = getDummyExpense();
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
		final Expense expense = getDummyExpense();
		final List<Expense> expenses = new ArrayList<>();
		expenses.add(expense);
		Mockito.when(this.expensesDao.getExpenses()).thenReturn(expenses);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content()
						.string(Matchers.containsString("Request processed successfully")));
	}

	@Test
	void getExportedExpenses_HttpGetRequest_HttpStatusOk() throws Exception {
		final Expense expense = getDummyExpense();
		final List<Expense> expenses = new ArrayList<>();
		expenses.add(expense);
		Mockito.when(this.expensesDao.getExpenses()).thenReturn(expenses);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses/export"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM));
	}

	@Test
	void updateExpense_Expense_HttpStatusOk() throws Exception {
		final Expense expense = getDummyExpense();
		Mockito.when(this.expensesDao.getExpense(any(Integer.class))).thenReturn(expense);
		Mockito.when(this.expensesDao.updateExpense(any(Expense.class))).thenReturn(true);
		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/api/v1/expenses")
						.content(this.objectMapper.writeValueAsString(expense)).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
	}

}
