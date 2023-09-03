package com.cgi.recipe.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.recipe.exception.RecipeNotFoundException;
import com.cgi.recipe.exception.RecipeServiceException;
import com.cgi.recipe.model.ErrorResponse;
import com.cgi.recipe.model.Ingredients;
import com.cgi.recipe.model.InputErrorMessage;
import com.cgi.recipe.model.RecipeResponse;
import com.cgi.recipe.service.intf.IGetRecipeInfoService;
import com.cgi.recipe.util.CommonConstants;



@Validated
@RestController
@RequestMapping("/recipe")
public class RecipeAppController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeAppController.class);
	
	@Autowired
	IGetRecipeInfoService recipeInfoService;
	
	@GetMapping
	public ResponseEntity<RecipeResponse> getRecipes() {
		LOGGER.debug("Inside getRecipes {}",this.getClass());
		RecipeResponse recipeResponse = recipeInfoService.getAllRecipes();
		return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getByIngredients")
	public ResponseEntity<RecipeResponse> getRecipesByIngredients(@Valid Ingredients ingredients
			) {
		LOGGER.debug("Inside getRecipesByIngredients {}",this.getClass());
		RecipeResponse recipeResponse = recipeInfoService.getAllRecipesByIngredients(ingredients.getIngredients());
		return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public InputErrorMessage handleMethodArgumentNotValid(BindException ex) {
		InputErrorMessage errorResponse = InputErrorMessage.builder().code(CommonConstants.STATUS_CODE_400).message(CommonConstants.INPUT_ERROR)
				.build();
		List<InputErrorMessage.InnerError> innerErrors = new ArrayList<>();
		errorResponse.setInnerErrors(innerErrors);
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			InputErrorMessage.InnerError innerError = InputErrorMessage.InnerError.builder().message(error.getDefaultMessage())
					.source(fieldName).build();
			innerErrors.add(innerError);
		});
		return errorResponse;
	}
	/**
	 * Handle not found exception
	 * 
	 * @param ex RecipeNotFoundException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RecipeNotFoundException.class)
	public ErrorResponse handleRecipeNotRecipe(RecipeNotFoundException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode("404").build();
		return errorResponse;
	}
	
	/**
	 * Handle RecipeServiceException
	 * 
	 * @param ex RecipeServiceException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RecipeServiceException.class)
	public ErrorResponse handleRecipeNotRecipe(RecipeServiceException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode("500").build();
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
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode("500").build();
		return errorResponse;
	}
}
