package feat_hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import dev.romanmarcu.MyApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = MyApplication.class)
public class HelloIT {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void helloShouldSayHi() throws Exception {
		Assertions.assertEquals("Hi !!!", this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class));
	}
}
