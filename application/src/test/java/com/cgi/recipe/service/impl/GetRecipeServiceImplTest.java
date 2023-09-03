package com.cgi.recipe.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cgi.recipe.exception.RecipeNotFoundException;
import com.cgi.recipe.exception.RecipeServiceException;
import com.cgi.recipe.model.RecipeResponse;

@ExtendWith(MockitoExtension.class)
public class GetRecipeServiceImplTest {

	@InjectMocks
	GetRecipeInfoServiceImpl getRecipeInfoService;
	
	@Test
	public void testGetAllRecipes() {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		RecipeResponse recipeResponse = getRecipeInfoService.getAllRecipes();
		assertNotNull(recipeResponse.getRecipes());
	}
	
	@Test
	public void testGetRecipeByIngredient() {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onions");
		RecipeResponse recipeResponse = getRecipeInfoService.getAllRecipesByIngredients(ingredientList);
		assertNotNull(recipeResponse.getRecipes());
	}
	
	@Test
	public void testRecipeNotFound() {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe.json");
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onionss");
		Assertions.assertThrows(RecipeNotFoundException.class,() -> getRecipeInfoService.getAllRecipesByIngredients(ingredientList));
	}
	
	@Test
	public void testServiceException() {
		org.springframework.test.util.ReflectionTestUtils.setField(getRecipeInfoService, "fileName", "receipe");
		List<String> ingredientList = new ArrayList<>();
		ingredientList.add("onionss");
		Assertions.assertThrows(RecipeServiceException.class,() -> getRecipeInfoService.getAllRecipesByIngredients(ingredientList));
	}

}
