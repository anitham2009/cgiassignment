package com.cgi.loganalyzer.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.loganalyzer.dao.ILogAnalyzerDao;
import com.cgi.loganalyzer.model.LogAnalyzerResponse;
import com.cgi.loganalyzer.model.LoggingDetail;
import com.cgi.loganalyzer.service.intf.ILoganalyzerService;

/**
 * Read log file and fetch log details and return response for given input log
 * type and top value.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class LogAnalyzerServiceImpl implements ILoganalyzerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzerServiceImpl.class);

	@Value("${loganalyzer.filename}")
	private String fileName;

	@Autowired
	ILogAnalyzerDao logAnalyzerDao;

	/**
	 * Read the log file and Sort the log detail by description and no. of
	 * occurrences Return the log detail response for given top value.
	 */
	@Override
	public LogAnalyzerResponse getLoggingDetail(String logType, int top) {
		LogAnalyzerResponse response = null;
		LOGGER.debug("Inside getLoggingDetail {}", this.getClass());
		// Read log file.
		LOGGER.info("Read log file {}", this.getClass());
		Map<String, LoggingDetail> loggingMap = logAnalyzerDao.getLogDetails(logType, fileName);
		if (!loggingMap.isEmpty()) {
			Set<String> descSet = loggingMap.keySet();
			// Set logging details in list.
			List<LoggingDetail> loggingDetails = new ArrayList<>();
			LOGGER.debug("Set log details in list {}", this.getClass());
			for (String desc : descSet) {
				LoggingDetail loggingDetail = loggingMap.get(desc);
				loggingDetails.add(loggingDetail);
			}
			LOGGER.debug("Filter logs by given input condition {}", this.getClass());
			List<LoggingDetail> responseLoggingDetails = null;
			// Compare by description and no of occurrences
			Comparator<LoggingDetail> compareByCond = Comparator.comparing(LoggingDetail::getNoOfOccurrences).reversed()
					.thenComparing(LoggingDetail::getDescription);
			// Select the log value by sorting the description and no. of occurrences and
			// select only N top occurrences of the list.
			responseLoggingDetails = loggingDetails.stream().sorted(compareByCond).limit(top)
					.collect(Collectors.toList());
			LOGGER.info("Set log details in response {}", this.getClass());
			// Set log details in response
			response = LogAnalyzerResponse.builder().loggingDetails(responseLoggingDetails).build();
		}
		return response;
	}

}