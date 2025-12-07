package com.fridge.tracker.external.interfaces.spoonacular.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpoonacularRecipe(
        String title,
        String image,
        List<SpoonacularIngredient> missedIngredients,
        List<SpoonacularIngredient> usedIngredients
) {
}
