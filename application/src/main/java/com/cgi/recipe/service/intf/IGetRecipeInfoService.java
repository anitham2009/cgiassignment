package com.cgi.recipe.service.intf;

import java.util.List;

import com.cgi.recipe.model.RecipeResponse;

public interface IGetRecipeInfoService {

	RecipeResponse getAllRecipes();

	RecipeResponse getAllRecipesByIngredients(List<String> ingredients);
}
