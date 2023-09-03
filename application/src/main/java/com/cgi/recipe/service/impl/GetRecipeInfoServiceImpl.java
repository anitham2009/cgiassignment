package com.cgi.recipe.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.recipe.exception.RecipeException;
import com.cgi.recipe.exception.RecipeInputException;
import com.cgi.recipe.exception.RecipeNotFoundException;
import com.cgi.recipe.exception.RecipeServiceException;
import com.cgi.recipe.model.Recipe;
import com.cgi.recipe.model.RecipeInfo;
import com.cgi.recipe.model.RecipeResponse;
import com.cgi.recipe.service.intf.IGetRecipeInfoService;
import com.cgi.recipe.util.CommonConstants;
import com.cgi.recipe.util.CommonUtil;

@Service
public class GetRecipeInfoServiceImpl implements IGetRecipeInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetRecipeInfoServiceImpl.class);
	
	@Value("${recipe.filename}")
	private String fileName;
	
	@Override
	public RecipeResponse getAllRecipes() {
		List<Recipe> recipeList = null;
		RecipeResponse response = null;
		try {
			LOGGER.debug("Inside getAllRecipes method {}", this.getClass());
			recipeList = CommonUtil.retrieveRecipes(fileName);
			if(recipeList.isEmpty()) {
				throw new RecipeNotFoundException(CommonConstants.RECIPE_NOT_EXISTS);
			}
			response = convertToResponseModel(recipeList);
		} catch (RecipeNotFoundException e) {
			throw e;
		}
		return response;
	}

	@Override
	public RecipeResponse getAllRecipesByIngredients(List<String> ingredients) {
		List<Recipe> recipeList = null;
		RecipeResponse response = null;
		try {
			LOGGER.debug("Inside getAllRecipesByIngredients method {}", this.getClass());
			recipeList = CommonUtil.retrieveRecipes(fileName);
			List<Recipe> responseList = recipeList.stream()
					.filter(item -> (Arrays.asList(item.getIngredients()).containsAll(ingredients)))
					.sorted((t1,t2) -> t1.getTitle().compareTo(t2.getTitle()))
					.collect(Collectors.toList());
			LOGGER.info("Retrived record {}",this.getClass());
			if(responseList.isEmpty()) {
				LOGGER.error("Recipe Not Found Exception {}", this.getClass());
				throw new RecipeNotFoundException(CommonConstants.RECIPE_NOT_EXISTS);
			}
			response = convertToResponseModel(responseList);
			LOGGER.debug("Converted into response model {}", this.getClass());
		} catch (RecipeNotFoundException e) {
			throw e;
		}

		return response;
	}

	public RecipeResponse convertToResponseModel(List<Recipe> recipe) {
		LOGGER.debug("Inside convertToResponseModel {}",this.getClass());
		List<RecipeInfo> recipeInfoList = recipe.stream().map(r -> {
			RecipeInfo recipeInfo = RecipeInfo.builder().href(r.getHref()).ingredients(r.getIngredients())
					.thumbnail(r.getThumbnail()).title(r.getTitle()).build();
			return recipeInfo;
		}).collect(Collectors.toList());
		RecipeResponse successResponse = RecipeResponse.builder().recipes(recipeInfoList).size(recipe.size()).build();
		return successResponse;
	}

}
