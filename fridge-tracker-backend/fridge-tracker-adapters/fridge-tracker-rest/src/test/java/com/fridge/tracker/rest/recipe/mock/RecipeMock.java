package com.fridge.tracker.rest.recipe.mock;

import com.fridge.tracker.domain.recipe.model.IngredientInfo;
import com.fridge.tracker.domain.recipe.model.Recipe;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RecipeMock {
    public static Recipe recipe = new Recipe("title", "image",
            List.of(new IngredientInfo("salt")), List.of(new IngredientInfo("pepper")));
}
