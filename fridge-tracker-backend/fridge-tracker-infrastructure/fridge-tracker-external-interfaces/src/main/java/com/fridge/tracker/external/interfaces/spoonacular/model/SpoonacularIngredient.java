package com.fridge.tracker.external.interfaces.spoonacular.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpoonacularIngredient(
        String original
) {
}
