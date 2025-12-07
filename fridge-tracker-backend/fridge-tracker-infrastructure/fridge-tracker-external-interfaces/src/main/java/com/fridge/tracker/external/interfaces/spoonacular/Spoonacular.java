package com.fridge.tracker.external.interfaces.spoonacular;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Spoonacular {

    @NoArgsConstructor(access = PRIVATE)
    public static final class API {
        public static final String FIND_RECIPES_BY_INGREDIENTS = "/recipes/findByIngredients";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class QueryParams {
        public static final String INGREDIENTS = "ingredients";
        public static final String API_KEY = "apiKey";
    }
}
