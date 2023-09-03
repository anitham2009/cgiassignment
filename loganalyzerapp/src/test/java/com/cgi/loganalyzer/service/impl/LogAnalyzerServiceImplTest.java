package com.cgi.loganalyzer.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgi.loganalyzer.dao.ILogAnalyzerDao;
import com.cgi.loganalyzer.exception.LogAnalyzerDaoException;
import com.cgi.loganalyzer.model.LogAnalyzerResponse;
import com.cgi.loganalyzer.model.LoggingDetail;
import com.cgi.loganalyzer.util.CommonConstants;

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

	@Mock
	ILogAnalyzerDao logAnalyzerDao;
	@DisplayName("Test get logging details success")
	@Test
	public void testGetLoggingDetail() {
		org.springframework.test.util.ReflectionTestUtils.setField(logAnalyzerService, "fileName",
				"logFile-2018-09-10.log");
		Map<String, LoggingDetail> loggingMap = new HashMap<>();
		loggingMap = getLogDetails("ERROR", "logFile-2018-09-10.log");
		when(logAnalyzerDao.getLogDetails(any(), any())).thenReturn(loggingMap);
		LogAnalyzerResponse logAnalyzerResponse = logAnalyzerService.getLoggingDetail("ERROR", 3);
		assertNotNull(logAnalyzerResponse);
	}

	@DisplayName("Test LogAnalyzerDaoException class")
	@Test
	public void testDaoException() {
		org.springframework.test.util.ReflectionTestUtils.setField(logAnalyzerService, "fileName",
				"logFile-2018-09-10");
		when(logAnalyzerDao.getLogDetails(any(), any())).thenThrow(LogAnalyzerDaoException.class);		
		Assertions.assertThrows(LogAnalyzerDaoException.class,
				() -> logAnalyzerService.getLoggingDetail("ERROR", 3));
	}
	@DisplayName("Test LogAnalyzerDaoException class")
	@Test
	public void testFileNotFoundDaoException() {
		org.springframework.test.util.ReflectionTestUtils.setField(logAnalyzerService, "fileName",
				"logFile-2018-09-10");
		when(logAnalyzerDao.getLogDetails(any(), any())).thenThrow(LogAnalyzerDaoException.class);		
		Assertions.assertThrows(LogAnalyzerDaoException.class,
				() -> logAnalyzerService.getLoggingDetail("ERROR", 3));
	}
	
	public static final String FILE_PATH = "src/test/resources/";
	/**
	 * Read the log file and description and no. of occurrences of given log type in
	 * the log file.
	 */
	public Map<String, LoggingDetail> getLogDetails(String logType, String fileName) {
		Map<String, LoggingDetail> loggingMap = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + fileName))) {
			String line;
			String description;
			while ((line = reader.readLine()) != null) {
				// Check given logType is available in the current log line.
				if (line.contains(logType.toUpperCase())) {
					// Get description
					int descriptionPosition = StringUtils.ordinalIndexOf(line, CommonConstants.COLON, 3);
					description = line.substring(descriptionPosition + 2);
					// Check description existence in map.
					if (loggingMap.get(description) != null) {
						LoggingDetail loggingDetail = loggingMap.get(description);
						int noOfOccurences = loggingDetail.getNoOfOccurrences();
						// Set no. of occurrences of description.
						loggingDetail.setNoOfOccurrences(++noOfOccurences);
					} else {
						// Set first occurrence of description in map.
						LoggingDetail loggingDetail = LoggingDetail.builder().description(description)
								.logType(logType.toUpperCase()).noOfOccurrences(1).build();
						loggingMap.put(description, loggingDetail);
					}

				}
			}
		} catch (FileNotFoundException e) {
			throw new LogAnalyzerDaoException(e.getMessage());
		} catch (IOException e) {
			throw new LogAnalyzerDaoException(e.getMessage());
		}
		return loggingMap;
	}

}
