package com.bebetto.financemanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bebetto.financemanager.pojo.Expense;
import com.bebetto.financemanager.response.Response;
import com.bebetto.financemanager.service.ExpensesService;
import com.bebetto.financemanager.validator.ExpenseIdConstraint;

@RestController
@RequestMapping("/api")
@Validated
public class ExpensesController {

	private final ExpensesService expensesService;

	@Autowired
	public ExpensesController(final ExpensesService expensesService) {
		this.expensesService = expensesService;
	}

	@PostMapping(value = "/v1/expenses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> createExpense(@Valid @RequestBody final Expense expense) {
		final HttpStatus httpStatus = HttpStatus.CREATED;
		final Map<String, Object> data = new HashMap<>();
		data.put("expenseId", this.expensesService.createExpense(expense));
		final Response<Map<String, Object>> response = new Response<>(httpStatus.value(), Response.DEFAULT_SUCCESS_MESSAGE,
				data);
		return new ResponseEntity<>(response, httpStatus);
	}

	@DeleteMapping(value = "/v1/expenses/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> deleteExpense(
			@PathVariable("expenseId") @ExpenseIdConstraint final int expenseId) {
		final HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		this.expensesService.deleteExpense(expenseId);
		return new ResponseEntity<>(httpStatus);
	}

	@GetMapping(value = "/v1/expenses/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> getExpense(
			@PathVariable("expenseId") @ExpenseIdConstraint final int expenseId) {
		final HttpStatus httpStatus = HttpStatus.OK;
		final Map<String, Object> data = new HashMap<>();
		data.put("expense", this.expensesService.getExpense(expenseId));
		final Response<Map<String, Object>> response = new Response<>(httpStatus.value(), Response.DEFAULT_SUCCESS_MESSAGE,
				data);
		return new ResponseEntity<>(response, httpStatus);
	}

	@GetMapping(value = "/v1/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> getExpenses() {
		final HttpStatus httpStatus = HttpStatus.OK;
		final Map<String, Object> data = new HashMap<>();
		data.put("expenses", this.expensesService.getExpenses());
		final Response<Map<String, Object>> response = new Response<>(httpStatus.value(), Response.DEFAULT_SUCCESS_MESSAGE,
				data);
		return new ResponseEntity<>(response, httpStatus);
	}

	@PutMapping(value = "/v1/expenses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> updateExpense(@RequestBody final Expense expense) {
		final HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		this.expensesService.updateExpense(expense);
		return new ResponseEntity<>(httpStatus);
	}

}
