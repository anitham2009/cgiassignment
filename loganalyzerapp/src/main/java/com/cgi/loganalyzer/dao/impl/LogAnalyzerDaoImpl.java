package com.cgi.loganalyzer.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cgi.loganalyzer.dao.ILogAnalyzerDao;
import com.cgi.loganalyzer.exception.LogAnalyzerDaoException;
import com.cgi.loganalyzer.model.LoggingDetail;
import com.cgi.loganalyzer.util.CommonConstants;

/**
 * Read log file and retrieve log details for the given log type.
 * 
 * @author Anitha Manoharan
 *
 */
@Repository
public class LogAnalyzerDaoImpl implements ILogAnalyzerDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzerDaoImpl.class);

	/**
	 * Read the log file and description and no. of occurrences of given log type in
	 * the log file.
	 */
	public Map<String, LoggingDetail> getLogDetails(String logType, String fileName) {
		LOGGER.debug("Inside getLogDetails {}", this.getClass());
		Map<String, LoggingDetail> loggingMap = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(CommonConstants.FILE_PATH + fileName))) {
			String line;
			String description;
			LOGGER.debug("Read log file {}", this.getClass());
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
			LOGGER.error("Error occurred in getLoggingDetail method " + e.getMessage() + " {} ", this.getClass());
			throw new LogAnalyzerDaoException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("IO Error occurred in getLoggingDetail method " + e.getMessage() + " {} ", this.getClass());
			throw new LogAnalyzerDaoException(e.getMessage());
		}
		return loggingMap;
	}
}
