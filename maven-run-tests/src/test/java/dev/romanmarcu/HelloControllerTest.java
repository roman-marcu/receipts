package dev.romanmarcu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {

	@Test
	void greetingShouldReturnMessageFromService() throws Exception {
		HelloController controller = new HelloController();
		Assertions.assertEquals("Hi !!!", controller.sayHi());
	}
}
