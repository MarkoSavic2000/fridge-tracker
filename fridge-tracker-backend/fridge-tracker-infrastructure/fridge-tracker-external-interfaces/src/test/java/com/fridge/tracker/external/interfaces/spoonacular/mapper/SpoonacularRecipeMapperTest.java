package com.fridge.tracker.external.interfaces.spoonacular.mapper;

import com.fridge.tracker.domain.recipe.model.IngredientInfo;
import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.external.interfaces.spoonacular.mock.SpoonacularRecipeMock;
import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularIngredient;
import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularRecipe;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpoonacularRecipeMapperTest {
    SpoonacularRecipeMapper mapper = new SpoonacularRecipeMapperImpl();

    @Test
    void map_returnListOfRecipes() {
        SpoonacularRecipe recipe = SpoonacularRecipeMock.recipe;
        List<SpoonacularRecipe> input = List.of(recipe);

        List<Recipe> result = mapper.map(input);

        assertNotNull(result);
        assertEquals(1, result.size());

        Recipe resultRecipe = result.getFirst();
        assertEquals(recipe.title(), resultRecipe.title());
        assertEquals(recipe.image(), resultRecipe.image());

        List<SpoonacularIngredient> spoonacularMissingIngredients = recipe.missedIngredients();
        List<IngredientInfo> missingIngredients = resultRecipe.missingIngredients();
        assertEquals(spoonacularMissingIngredients.size(), missingIngredients.size());
        assertEquals(spoonacularMissingIngredients.getFirst().original(), missingIngredients.getFirst().ingredient());

        List<SpoonacularIngredient> spoonacularPresentIngredients = recipe.usedIngredients();
        List<IngredientInfo> presentIngredients = resultRecipe.presentIngredients();
        assertEquals(spoonacularPresentIngredients.size(), presentIngredients.size());
        assertEquals(spoonacularMissingIngredients.getFirst().original(), missingIngredients.getFirst().ingredient());
    }
}