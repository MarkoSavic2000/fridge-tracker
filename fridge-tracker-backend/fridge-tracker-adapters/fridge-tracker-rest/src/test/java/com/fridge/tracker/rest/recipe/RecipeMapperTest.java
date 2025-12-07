package com.fridge.tracker.rest.recipe;

import com.fridge.tracker.domain.recipe.model.IngredientInfo;
import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.rest.model.GetRecipesResponse;
import com.fridge.tracker.rest.model.IngredientInfoApi;
import com.fridge.tracker.rest.model.RecipeApi;
import com.fridge.tracker.rest.recipe.mock.RecipeMock;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeMapperTest {
    RecipeMapper mapper = new RecipeMapperImpl();

    @Test
    void map_returnGetRecipesResponse() {
        Recipe recipe = RecipeMock.recipe;
        List<Recipe> input = List.of(recipe);

        GetRecipesResponse response = mapper.map(input);

        assertNotNull(response);

        List<RecipeApi> recipes = response.getRecipes();
        assertEquals(1, recipes.size());

        RecipeApi recipeApi = recipes.getFirst();
        assertEquals(recipe.image(), recipeApi.getImage());
        assertEquals(recipe.title(), recipeApi.getTitle());

        List<IngredientInfo> missingIngredients = recipe.missingIngredients();
        List<IngredientInfoApi> missingIngredientsApi = recipeApi.getMissingIngredients();
        assertEquals(missingIngredients.size(), missingIngredientsApi.size());
        assertEquals(missingIngredients.getFirst().ingredient(), missingIngredientsApi.getFirst().getIngredient());

        List<IngredientInfo> presentIngredients = recipe.presentIngredients();
        List<IngredientInfoApi> presentIngredientsApi = recipeApi.getPresentIngredients();
        assertEquals(presentIngredients.size(), presentIngredientsApi.size());
        assertEquals(presentIngredients.getFirst().ingredient(), presentIngredientsApi.getFirst().getIngredient());
    }
}