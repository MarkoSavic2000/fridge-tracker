package com.fridge.tracker.external.interfaces.spoonacular.mock;

import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularIngredient;
import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularRecipe;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class SpoonacularRecipeMock {

    public static SpoonacularRecipe recipe = new SpoonacularRecipe("title", "image",
            List.of(new SpoonacularIngredient("salt")), List.of(new SpoonacularIngredient("pepper")));
}
