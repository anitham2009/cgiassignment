package com.cgi.recipe.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cgi.recipe.dao.IRecipeDao;
import com.cgi.recipe.exception.RecipeDaoException;
import com.cgi.recipe.model.Recipe;
import com.cgi.recipe.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Get all recipes from json.
 * 
 * @author Anitha Manoharan
 *
 */
@Repository
public class RecipeDaoImpl implements IRecipeDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDaoImpl.class);

	/**
	 * Read json file and converts into list of Recipe object
	 * 
	 * @param fileName
	 * @return List<Recipe>
	 */
	@Override
	public List<Recipe> getAllRecipes(String fileName) {
		File file = new File(CommonConstants.FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		List<Recipe> recipes;
		try {
			LOGGER.debug("Convert json into Recipe object {}", RecipeDaoImpl.class);
			recipes = Arrays.asList(mapper.readValue(file, Recipe[].class));
		} catch (IOException e) {
			LOGGER.error("Error occurred in getAllRecipes" + e.getMessage() + " {}", RecipeDaoImpl.class);
			throw new RecipeDaoException(e.getMessage());
		}
		return recipes;
	}

}
