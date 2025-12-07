package com.fridge.tracker.domain.recipe;

import com.fridge.tracker.domain.recipe.model.Recipe;

import java.util.List;

public interface RecipeGenerator {

    /**
     * Generates recipes based on ingredients.
     *
     * @param ingredients ingredients that should be part of recipes
     * @return list of {@link Recipe}
     */
    List<Recipe> generate(List<String> ingredients);
}
