package com.cgi.recipe.exception;

/**
 * Custom dao exception
 * @author Anitha Manoharan
 *
 */
public class RecipeDaoException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecipeDaoException(final String message) {
		super(message);
		
	}

}
