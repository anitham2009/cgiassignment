package com.cgi.recipe.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.recipe.dao.IRecipeDao;
import com.cgi.recipe.exception.RecipeDaoException;
import com.cgi.recipe.exception.RecipeNotFoundException;
import com.cgi.recipe.model.Recipe;
import com.cgi.recipe.model.RecipeInfo;
import com.cgi.recipe.model.RecipeResponse;
import com.cgi.recipe.service.intf.IGetRecipeInfoService;
import com.cgi.recipe.util.CommonConstants;

/**
 * Get recipe details.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
public class GetRecipeInfoServiceImpl implements IGetRecipeInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetRecipeInfoServiceImpl.class);

	@Autowired
	private IRecipeDao recipeDao;

	// Get recipe file name.
	@Value("${recipe.filename}")
	private String fileName;

	/**
	 * Read receipe.json file Get all recipes from file and convert into response
	 * model Return recipe response.
	 */
	@Override
	public RecipeResponse getAllRecipes() {
		List<Recipe> recipeList = null;
		RecipeResponse response = null;
		try {
			LOGGER.debug("Inside getAllRecipes method {}", this.getClass());
			recipeList = recipeDao.getAllRecipes(fileName);
			if (recipeList.isEmpty()) {
				LOGGER.error("Recipe not found");
				throw new RecipeNotFoundException(CommonConstants.RECIPE_NOT_EXISTS);
			}
			response = convertToResponseModel(recipeList);
		} catch (RecipeNotFoundException e) {
			throw e;
		} catch (RecipeDaoException e) {
			throw e;
		}
		return response;
	}

	/**
	 * Read receipe.json file Get all recipes from file and filter recipes which has
	 * given input ingredients and convert into response model Return recipe
	 * response.
	 */
	@Override
	public RecipeResponse getAllRecipesByIngredients(List<String> ingredients) {
		List<Recipe> recipeList = null;
		RecipeResponse response = null;
		try {
			LOGGER.debug("Inside getAllRecipesByIngredients method {}", this.getClass());
			recipeList = recipeDao.getAllRecipes(fileName);
			List<Recipe> responseList = recipeList.stream()
					.filter(item -> (Arrays.asList(item.getIngredients()).containsAll(ingredients)))
					.sorted((t1, t2) -> t1.getTitle().compareTo(t2.getTitle())).collect(Collectors.toList());
			LOGGER.info("Retrived record {}", this.getClass());
			if (responseList.isEmpty()) {
				LOGGER.error("Recipe Not Found Exception {}", this.getClass());
				throw new RecipeNotFoundException(CommonConstants.RECIPE_NOT_EXISTS);
			}
			response = convertToResponseModel(responseList);
			LOGGER.debug("Converted into response model {}", this.getClass());
		} catch (RecipeNotFoundException e) {
			throw e;
		} catch (RecipeDaoException e) {
			throw e;
		}

		return response;
	}

	/**
	 * Convert recipe list into response model.
	 * 
	 * @param recipe List<Recipe>
	 * @return RecipeResponse
	 */
	public RecipeResponse convertToResponseModel(List<Recipe> recipe) {
		LOGGER.debug("Inside convertToResponseModel {}", this.getClass());
		List<RecipeInfo> recipeInfoList = recipe.stream().map(r -> {
			RecipeInfo recipeInfo = RecipeInfo.builder().href(r.getHref()).ingredients(r.getIngredients())
					.thumbnail(r.getThumbnail()).title(r.getTitle()).build();
			return recipeInfo;
		}).collect(Collectors.toList());
		RecipeResponse successResponse = RecipeResponse.builder().recipes(recipeInfoList).size(recipe.size()).build();
		return successResponse;
	}

}
