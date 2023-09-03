package com.cgi.recipe.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.cgi.recipe.model.Recipe;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	public static final String FILE_PATH = "src/test/resources/";
	/**
	 * Read json file and converts into list of Recipe object
	 * 
	 * @param fileName
	 * @return List<Recipe>
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	public static List<Recipe> retrieveRecipes(String fileName) throws StreamReadException, DatabindException, IOException {
		File file = new File(FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		List<Recipe> recipes;
			recipes = Arrays.asList(mapper.readValue(file, Recipe[].class));
		return recipes;

	}
}
