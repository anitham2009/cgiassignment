package com.cgi.loganalyzer.resource;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.loganalyzer.exception.LogAnalyzerDaoException;
import com.cgi.loganalyzer.model.ErrorResponse;
import com.cgi.loganalyzer.model.LogAnalyzerResponse;
import com.cgi.loganalyzer.service.intf.ILoganalyzerService;
import com.cgi.loganalyzer.util.CommonConstants;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Return log file content by log type.
 * 
 * @author Anitha Manoharan
 *
 */
@Validated
@RestController
@RequestMapping("/logging")
public class LogAnalyzerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzerController.class);

	@Autowired
	ILoganalyzerService logAnalyzerService;

	/**
	 * Analyze log file and return log details for the given log type.
	 * 
	 * @param logType log type
	 * @param top     top value
	 * @return
	 */
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = LogAnalyzerResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping
	public ResponseEntity<LogAnalyzerResponse> getLoggingDetail(
			@Schema(example = "ERROR") @RequestParam(name = "logType", required = true) @Valid @Pattern(regexp = "ERROR|WARN|DEBUG|INFO", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid log type. Log type should be ERROR/WARN/DEBUG/INFO") String logType,
			@RequestParam(name = "top", required = true) @Valid @Min(value = 1, message = "Invalid top value") @Schema(example = "3") int top) {
		LOGGER.debug("Inside getLoggingDetail {}", this.getClass());
		LogAnalyzerResponse recipeResponse = logAnalyzerService.getLoggingDetail(logType, top);
		return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
	}

	/**
	 * Handles Invalid input exception
	 * 
	 * @param ex exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorResponse handleMethodArgumentNotValid(ConstraintViolationException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage())
				.statusCode(CommonConstants.STATUS_CODE_400).build();
		return errorResponse;
	}

	/**
	 * Handle LogAnalyzerDaoException
	 * 
	 * @param ex LogAnalyzerDaoException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(LogAnalyzerDaoException.class)
	public ErrorResponse handleDaoException(LogAnalyzerDaoException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage())
				.statusCode(CommonConstants.STATUS_CODE_500).build();
		return errorResponse;
	}

	/**
	 * Handles Internal server error.
	 * 
	 * @param ex Exception
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleInternalServerError(Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage())
				.statusCode(CommonConstants.STATUS_CODE_500).build();
		return errorResponse;
	}

}
