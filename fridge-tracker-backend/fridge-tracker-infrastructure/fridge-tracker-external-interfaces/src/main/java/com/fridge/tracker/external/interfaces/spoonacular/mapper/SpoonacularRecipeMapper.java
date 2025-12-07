package com.fridge.tracker.external.interfaces.spoonacular.mapper;

import com.fridge.tracker.domain.recipe.model.IngredientInfo;
import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularIngredient;
import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularRecipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SpoonacularRecipeMapper {

    @Mapping(source = "original", target = "ingredient")
    IngredientInfo map(SpoonacularIngredient ingredient);

    List<IngredientInfo> mapIngredients(List<SpoonacularIngredient> ingredients);

    @Mapping(source = "missedIngredients", target = "missingIngredients")
    @Mapping(source = "usedIngredients", target = "presentIngredients")
    Recipe map(SpoonacularRecipe recipe);

    List<Recipe> map(List<SpoonacularRecipe> recipes);
}
