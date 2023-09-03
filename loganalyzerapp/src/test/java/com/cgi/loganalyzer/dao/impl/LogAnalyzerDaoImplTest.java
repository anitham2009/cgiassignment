package com.cgi.loganalyzer.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgi.loganalyzer.exception.LogAnalyzerDaoException;

/**
 * Test LogAnalyzerDaoImpl class
 * 
 * @author Anitha Manoharan
 *
 */
@ExtendWith(MockitoExtension.class)
public class LogAnalyzerDaoImplTest {

	@InjectMocks
	LogAnalyzerDaoImpl logAnalyzerDao;

	@DisplayName("Test get logging details with Invalid file name")
	@Test
	public void testGetLoggingDetailInvalidFile() {
		Assertions.assertThrows(LogAnalyzerDaoException.class, () -> logAnalyzerDao.getLogDetails("ERROR", "ex.log"));

	}
}
