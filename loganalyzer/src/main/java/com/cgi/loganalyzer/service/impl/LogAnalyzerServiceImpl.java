package com.cgi.loganalyzer.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.loganalyzer.exception.LogAnalyzerServiceException;
import com.cgi.loganalyzer.model.LogAnalyzerResponse;
import com.cgi.loganalyzer.model.LoggingDetail;
import com.cgi.loganalyzer.service.intf.ILoganalyzerService;
import com.cgi.loganalyzer.util.CommonConstants;

/**
 * Read log file and fetch logdetails and return response for given input log type and top value.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class LogAnalyzerServiceImpl implements ILoganalyzerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzerServiceImpl.class);
	
	@Value("${loganalyzer.filename}")
	private String fileName;
	
	/**
	 *  Read the log file and Sort the log detail by description and no. of occurrences 
	 *  Return the log detail response for given top value.
	 */
	@Override
	public LogAnalyzerResponse getLoggingDetail(String logType, int top) {
		LogAnalyzerResponse response = null;
		LOGGER.debug("Inside getLoggingDetail {}", this.getClass());
		// Read log file.
		try (BufferedReader reader = new BufferedReader(new FileReader(CommonConstants.FILE_PATH+fileName))) {
			String line;
			String description;
			Map<String, LoggingDetail> loggingMap = new HashMap<>();

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
			if (!loggingMap.isEmpty()) {
				Set<String> descSet = loggingMap.keySet();
				// Set logging details in list.
				List<LoggingDetail> loggingDetails = new ArrayList<>();
				for (String desc : descSet) {
					LoggingDetail loggingDetail = loggingMap.get(desc);
					loggingDetails.add(loggingDetail);
				}
				List<LoggingDetail> responseLoggingDetails = null;
				// Compare by description and no of occurrences
				Comparator<LoggingDetail> compareByCond = Comparator.comparing(LoggingDetail::getNoOfOccurrences)
						.reversed().thenComparing(LoggingDetail::getDescription);
				// Select the log value by sorting the description and no. of occurrences and
				// select only N top occurrences of the list.
				if (top != 0) {
					responseLoggingDetails = loggingDetails.stream().sorted(compareByCond).limit(top)
							.collect(Collectors.toList());
				} else {
					responseLoggingDetails = loggingDetails.stream().sorted(compareByCond).collect(Collectors.toList());
				}
				// Set log details in response
				response = LogAnalyzerResponse.builder().loggingDetails(responseLoggingDetails).build();
			}
		} catch (FileNotFoundException e) {
			throw new LogAnalyzerServiceException(e.getMessage());
		} catch (IOException e) {
			throw new LogAnalyzerServiceException(e.getMessage());
		}
		return response;
	}

}
