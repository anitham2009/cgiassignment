package com.cgi.recipe.exception;
/**
 * Custom Recipe not found exception.
 * @author Anitha Manoharan
 *
 */
public class RecipeNotFoundException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecipeNotFoundException(final String message) {
		super(message);
		
	}

}
