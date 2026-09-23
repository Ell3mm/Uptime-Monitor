package com.uptimemonitor.api.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MonitorControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createsMonitorWithValidRequest() throws Exception {
		mockMvc.perform(post("/api/monitors")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"name":"Example","url":"https://example.com","intervalSeconds":60,"timeoutSeconds":10,"enabled":true}
							"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Example"))
				.andExpect(jsonPath("$.url").value("https://example.com"))
				.andExpect(jsonPath("$.status").doesNotExist());
	}

	@Test
	void rejectsInvalidUrl() throws Exception {
		mockMvc.perform(post("/api/monitors")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"name":"Example","url":"not-a-url","intervalSeconds":60,"timeoutSeconds":10,"enabled":true}
							"""))
				.andExpect(status().isBadRequest());
	}
}