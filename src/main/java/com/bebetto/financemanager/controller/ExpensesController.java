package com.bebetto.financemanager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@GetMapping(value = "/v1/expenses")
	public ResponseEntity<Response<Map<String, Object>>> getExpenses() {
		final Map<String, Object> data = new HashMap<>();
		data.put("expenses", expensesService.getExpenses());
		final Response<Map<String, Object>> response = new Response<>(200, "Request processed successfully...!", data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/v1/expenses/{expenseId}")
	public ResponseEntity<Response<Map<String, Object>>> getExpense(@PathVariable("expenseId") int expenseId) {
		final Map<String, Object> data = new HashMap<>();
		data.put("expense", expensesService.getExpense());
		final Response<Map<String, Object>> response = new Response<>(200, "Request processed successfully...!", data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
