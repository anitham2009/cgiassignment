package com.cgi.recipe.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cgi.recipe.util.CommonConstants;

/**
 * Validate recipe ingredient inputs and return status.
 * 
 * @author Anitha Manoharan
 *
 */
public class RecipeIngredientValidator implements ConstraintValidator<RecipeInputValidation, List<String>> {
	@Override
	public boolean isValid(List<String> ingredients, ConstraintValidatorContext context) {

		Pattern pattern = Pattern.compile(CommonConstants.STR_REGEX);
		// Input value required check.
		if (ingredients.isEmpty()) {
			return false;
		}

		for (String ingredient : ingredients) {
			// Allow only strings check.
			Matcher match = pattern.matcher(ingredient);
			if (!match.find()) {
				return false;
			}
		}
		return true;
	}
}