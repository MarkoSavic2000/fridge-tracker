package com.fridge.tracker.rest.recipe;

import com.fridge.tracker.application.recipe.generate.GenerateRecipesCommand;
import com.fridge.tracker.application.shared.cqrs.Bus;
import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.rest.RecipeApi;
import com.fridge.tracker.rest.model.GetRecipesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController implements RecipeApi {
    private final Bus bus;
    private final RecipeMapper mapper;

    @Override
    public ResponseEntity<GetRecipesResponse> getRecipes(Long fridgeId) {
        var command = new GenerateRecipesCommand(fridgeId);
        List<Recipe> result = bus.execute(command);
        GetRecipesResponse response = mapper.map(result);
        return ResponseEntity.ok(response);
    }
}
