package com.bebetto.financemanager;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class ExpensesTests {

	private final MockMvc mockMvc;

	@Autowired
	public ExpensesTests(final MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	void getExpenses_HttpGetRequest_HttpStatusOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/expenses")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content()
						.string(Matchers.containsString("Request processed successfully")));
	}

}
