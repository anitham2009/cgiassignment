package com.cgi.loganalyzer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Log analyzer response
 * @author Anitha Manoharan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAnalyzerResponse {

	List<LoggingDetail> loggingDetails;
}
