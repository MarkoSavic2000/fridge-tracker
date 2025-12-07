package com.fridge.tracker.domain.recipe.model;

import java.util.List;

public record Recipe(
        String title,
        String image,
        List<IngredientInfo> missingIngredients,
        List<IngredientInfo> presentIngredients
) {
}
