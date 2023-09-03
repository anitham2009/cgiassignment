package com.cgi.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class for Recipe value object
 * @author Anitha Manoharan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeInfo {

	private String title;
	private String href;
	private String[] ingredients;
	private String thumbnail;
}

