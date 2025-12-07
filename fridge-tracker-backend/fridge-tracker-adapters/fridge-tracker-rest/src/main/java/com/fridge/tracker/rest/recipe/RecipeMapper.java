package com.fridge.tracker.rest.recipe;

import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.rest.model.GetRecipesResponse;
import com.fridge.tracker.rest.model.RecipeApi;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RecipeMapper {

    List<RecipeApi> mapRecipes(List<Recipe> recipes);

    default GetRecipesResponse map(List<Recipe> recipes) {
        return new GetRecipesResponse()
                .recipes(mapRecipes(recipes));
    }
}
