package com.cgi.recipe.exception;
/**
 * Custom input exception
 * @author Anitha Manoharan
 *
 */
public class RecipeInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecipeInputException(final String message) {
		super(message);
		
	}
}
