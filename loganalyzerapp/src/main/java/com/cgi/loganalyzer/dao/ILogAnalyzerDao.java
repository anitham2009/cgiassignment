package com.cgi.loganalyzer.dao;

import java.util.Map;

import com.cgi.loganalyzer.model.LoggingDetail;

/**
 * Loganalyzer dao
 * 
 * @author Anitha Manoharan
 *
 */
public interface ILogAnalyzerDao {
	Map<String, LoggingDetail> getLogDetails(String logType, String fileName);
}
