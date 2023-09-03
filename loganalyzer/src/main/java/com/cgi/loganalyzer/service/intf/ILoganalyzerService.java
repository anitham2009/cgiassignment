package com.cgi.loganalyzer.service.intf;

import com.cgi.loganalyzer.model.LogAnalyzerResponse;
/**
 * Log analyzer service interface
 * @author Anitha Manoharan
 *
 */
public interface ILoganalyzerService {

	public LogAnalyzerResponse getLoggingDetail(String logType, int top);
	
}
