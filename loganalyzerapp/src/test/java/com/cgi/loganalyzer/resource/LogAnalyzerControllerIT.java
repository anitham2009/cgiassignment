package com.cgi.loganalyzer.resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Integration Test Log analyzer controller
 * 
 * @author Anitha Manoharan
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class LogAnalyzerControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private LogAnalyzerController logAnalyzerController;

	@DisplayName("Test Get Logging Detail success")
	@Test
	public void testGetLogging() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/logging/").param("logType", "ERROR").param("top", "3")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
		assertNotNull(result.getResponse());
	}

	@DisplayName("Test Invalid input while retrive logging detail")
	@Test
	public void testInvalidInput() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/logging/").param("logType", "err").param("top", "0")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().isBadRequest()).andReturn();
		assertNotNull(result.getResponse());
	}

	@DisplayName("Test Internal server error")
	@Test
	public void testInternalServerError() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/logging/").param("logTyp", "err").param("top", "0")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
		assertNotNull(result.getResponse());
	}
}
