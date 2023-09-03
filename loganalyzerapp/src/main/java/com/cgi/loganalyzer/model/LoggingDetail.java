package com.cgi.loganalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Log details
 * @author Anitha Manoharan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggingDetail {

	private String logType;
	private String description;
	private int noOfOccurrences;
}
