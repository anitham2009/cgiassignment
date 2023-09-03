package com.cgi.loganalyzer.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgi.loganalyzer.exception.LogAnalyzerServiceException;
import com.cgi.loganalyzer.model.LogAnalyzerResponse;

/**
 * Test LogAnalyzerServiceImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class LogAnalyzerServiceImplTest {

	@InjectMocks
	LogAnalyzerServiceImpl logAnalyzerService;

	@DisplayName("Test get logging details success")
	@Test
	public void testGetLoggingDetail() {
		org.springframework.test.util.ReflectionTestUtils.setField(logAnalyzerService, "fileName",
				"logFile-2018-09-10.log");
		LogAnalyzerResponse logAnalyzerResponse = logAnalyzerService.getLoggingDetail("ERROR", 3);
		assertNotNull(logAnalyzerResponse);
	}

	@DisplayName("Test  get logging details success for top 0 value")
	@Test
	public void testGetLoggingDetailTopZero() {
		org.springframework.test.util.ReflectionTestUtils.setField(logAnalyzerService, "fileName",
				"logFile-2018-09-10.log");
		LogAnalyzerResponse logAnalyzerResponse = logAnalyzerService.getLoggingDetail("ERROR", 0);
		assertNotNull(logAnalyzerResponse);
	}

	@DisplayName("Test LogAnalyzerServiceException class")
	@Test
	public void testServiceException() {
		org.springframework.test.util.ReflectionTestUtils.setField(logAnalyzerService, "fileName",
				"logFile-2018-09-10");
		Assertions.assertThrows(LogAnalyzerServiceException.class,
				() -> logAnalyzerService.getLoggingDetail("ERROR", 3));
	}

}
