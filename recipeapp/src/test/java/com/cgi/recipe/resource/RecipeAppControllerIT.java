package com.cgi.recipe.resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class RecipeAppControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	RecipeAppController recipeAppController;
	
	
	@Test
	public void testGetAllRecipes() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/recipe/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
	 assertNotNull(result.getResponse());
		
	}
	
	@Test
	public void testRecipeByIngredients() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/recipe/getByIngredients").param("ingredients", "onions").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
	 assertNotNull(result.getResponse());
	}
	
	
	@Test
	public void testEmptyResponse() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/recipe/getByIngredients").param("ingredients", "onionss").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().isNotFound()).andReturn();
	 assertNotNull(result.getResponse());
	}
	@Test
	public void testInvalidInput() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/recipe/getByIngredients").param("ingredients", "1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().isBadRequest()).andReturn();
	 assertNotNull(result.getResponse());
	}
	
	@Test
	public void testInternalServerError() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/recipe/getByIngredients").param("ingredient", "1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
	 assertNotNull(result.getResponse());
	}
	
}
