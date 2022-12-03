package com.bebetto.financemanager.property;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExpensesTestDataLoader {

	private static final String EXPENSES_TEST_DATA_FILE = "expenses-test-data.json";

	private final JsonNode expensesTestDataJsonNode;
	private final ObjectMapper objectMapper;

	@Autowired
	public ExpensesTestDataLoader(final ObjectMapper objectMapper) throws IOException {
		this.objectMapper = objectMapper;
		this.expensesTestDataJsonNode = getExpensesTestDataJsonNodeFromFile();
	}

	private JsonNode getExpensesTestDataJsonNodeFromFile() throws IOException {
		final Resource resource = new ClassPathResource(EXPENSES_TEST_DATA_FILE);
		return this.objectMapper.readTree(resource.getFile());
	}

	public JsonNode getExpensesTestDataNode() {
		return this.expensesTestDataJsonNode;
	}

}
