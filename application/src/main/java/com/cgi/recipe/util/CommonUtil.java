package com.cgi.recipe.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.cgi.recipe.exception.RecipeException;
import com.cgi.recipe.exception.RecipeServiceException;
import com.cgi.recipe.model.Recipe;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {


	/**
	 * Read json file and converts into object of given class type
	 * 
	 * @param <T>
	 * @param fileName
	 * @param contentClass
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public static List<Recipe> retrieveRecipes(String fileName) {
		
		File file = new File(CommonConstants.FILE_PATH+fileName);
		ObjectMapper mapper = new ObjectMapper();
		List<Recipe> recipes;
		try {
			recipes = Arrays.asList(mapper.readValue(file, Recipe[].class));
		} catch (IOException e) {
			 throw new RecipeServiceException(e.getMessage());
		}
		return recipes;

	}
}
