package com.cgi.recipe.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgi.recipe.dao.IRecipeDao;
import com.cgi.recipe.exception.RecipeDaoException;
import com.cgi.recipe.exception.RecipeNotFoundException;
import com.cgi.recipe.model.Recipe;
import com.cgi.recipe.model.RecipeResponse;
import com.cgi.recipe.util.CommonUtil;

@ExtendWith(MockitoExtension.class)
public class GetRecipeServiceImplTest {

	@InjectMocks
	GetRecipeInfoServiceImpl getRecipeInfoService;

	@Mock
	IRecipeDao recipeDao;

	@Test
	public void testGetAllRecipes() throws Exception {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		List<Recipe> recipes = null;
		recipes = CommonUtil.retrieveRecipes("receipe.json");
		when(recipeDao.getAllRecipes(any())).thenReturn(recipes);
		RecipeResponse recipeResponse = getRecipeInfoService.getAllRecipes();
		assertNotNull(recipeResponse.getRecipes());
	}
	@Test
	public void testRecipesNotFound() throws Exception {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		List<Recipe> recipes = new ArrayList<>();
		when(recipeDao.getAllRecipes(any())).thenReturn(recipes);
		Assertions.assertThrows(RecipeNotFoundException.class,
				() -> getRecipeInfoService.getAllRecipes());
	}
	@Test
	public void testGetAllRecipeDaoException() throws Exception {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe");
		when(recipeDao.getAllRecipes(any())).thenThrow(RecipeDaoException.class);
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onionss");
		Assertions.assertThrows(RecipeDaoException.class,
				() -> getRecipeInfoService.getAllRecipes());
	}


	@Test
	public void testGetRecipeByIngredient() throws Exception {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		List<Recipe> recipes = null;
		recipes = CommonUtil.retrieveRecipes("receipe.json");
		when(recipeDao.getAllRecipes(any())).thenReturn(recipes);
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onions");
		RecipeResponse recipeResponse = getRecipeInfoService.getAllRecipesByIngredients(ingredientList);
		assertNotNull(recipeResponse.getRecipes());
	}

	@Test
	public void testRecipeNotFound() throws Exception {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		List<Recipe> recipes = null;
		recipes = CommonUtil.retrieveRecipes("receipe.json");
		when(recipeDao.getAllRecipes(any())).thenReturn(recipes);
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onionss");
		Assertions.assertThrows(RecipeNotFoundException.class,
				() -> getRecipeInfoService.getAllRecipesByIngredients(ingredientList));
	}

	@Test
	public void testDaoException() throws Exception {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe");
		when(recipeDao.getAllRecipes(any())).thenThrow(RecipeDaoException.class);
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onionss");
		Assertions.assertThrows(RecipeDaoException.class,
				() -> getRecipeInfoService.getAllRecipesByIngredients(ingredientList));
	}

}
