package com.bebetto.financemanager.property;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.bebetto.financemanager.logger.LoggingManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExpensesTestDataLoader {

	private static final String EXPENSES_TEST_DATA_FILE = "expenses-test-data.json";
	private final JsonNode expensesTestDataJsonNode;
	private final ObjectMapper objectMapper;

	@Autowired
	public ExpensesTestDataLoader(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.expensesTestDataJsonNode = getExpensesTestDataJsonNodeFromFile();
	}

	private JsonNode getExpensesTestDataJsonNodeFromFile() {
		JsonNode jsonNode = null;
		try {
			final Resource resource = new ClassPathResource(EXPENSES_TEST_DATA_FILE);
			jsonNode = this.objectMapper.readTree(resource.getFile());
		} catch (final IOException exc) {
			LoggingManager.error("Exception while loading " + EXPENSES_TEST_DATA_FILE + " file...!", exc);
		}
		return jsonNode;
	}

	public JsonNode getExpensesTestDataNode() {
		return this.expensesTestDataJsonNode;
	}

}
