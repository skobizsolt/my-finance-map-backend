package com.myfinancemap.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AppApplicationTests {

	private final ApplicationContext context;

	AppApplicationTests(ApplicationContext context) {
		this.context = context;
	}

	@Test
	void contextLoads() {
		assertEquals("application", context.getId());
	}

}
