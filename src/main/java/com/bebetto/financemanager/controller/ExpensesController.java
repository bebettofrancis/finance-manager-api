package com.bebetto.financemanager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bebetto.financemanager.response.Response;
import com.bebetto.financemanager.service.ExpensesService;

@RestController
@RequestMapping("/api")
public class ExpensesController {

	private final ExpensesService expensesService;

	@Autowired
	public ExpensesController(final ExpensesService expensesService) {
		this.expensesService = expensesService;
	}

	@GetMapping(value = "/v1/expenses/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> getExpense(@PathVariable("expenseId") final int expenseId) {
		final Map<String, Object> data = new HashMap<>();
		data.put("expense", this.expensesService.getExpense(expenseId));
		final Response<Map<String, Object>> response = new Response<>(HttpStatus.OK.value(), Response.DEFAULT_MESSAGE,
				data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/v1/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Map<String, Object>>> getExpenses() {
		final Map<String, Object> data = new HashMap<>();
		data.put("expenses", this.expensesService.getExpenses());
		final Response<Map<String, Object>> response = new Response<>(HttpStatus.OK.value(), Response.DEFAULT_MESSAGE,
				data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
