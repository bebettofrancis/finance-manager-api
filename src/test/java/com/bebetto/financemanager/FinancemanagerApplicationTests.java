package com.bebetto.financemanager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.bebetto.financemanager.controller.ExpensesController;

@SpringBootTest
@DirtiesContext
class FinancemanagerApplicationTests {

	private final ExpensesController expensesController;

	@Autowired
	public FinancemanagerApplicationTests(final ExpensesController expensesController) {
		this.expensesController = expensesController;
	}

	@Test
	void contextLoads() {
		Assertions.assertThat(this.expensesController).isNotNull();
	}

}
