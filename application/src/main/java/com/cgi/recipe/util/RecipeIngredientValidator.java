package com.cgi.recipe.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class RecipeIngredientValidator implements ConstraintValidator<RecipeInputValidation, List<String>>
{
	@Override
	public boolean isValid(List<String> ingredients, ConstraintValidatorContext context) {
		Pattern pattern = Pattern.compile(CommonConstants.STR_REGEX);
		if (ingredients.isEmpty()) {
			return false;
		}
		
		for (String ingredient : ingredients) {
			Matcher match = pattern.matcher(ingredient);
			if (!match.find()) {
				return false;
			}
		}
		return true;
	}
}