package com.cgi.recipe.model;

import java.io.Serializable;
import java.util.List;

import com.cgi.recipe.util.RecipeInputValidation;

public class Ingredients implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RecipeInputValidation
	private List<String> ingredients;
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}



}
