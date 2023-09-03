package com.cgi.recipe.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom annotation for validate inputs for recipe ingredients
 * @author Anitha Manoharan
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RecipeIngredientValidator.class)
@Documented
public @interface RecipeInputValidation {
	String message() default "Invalid input for ingredients";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}