package com.cgi.recipe.dao;

import java.util.List;

/**
 * RecipeDao
 */
import com.cgi.recipe.model.Recipe;
public interface IRecipeDao {
	List<Recipe> getAllRecipes(String fileName);
}
