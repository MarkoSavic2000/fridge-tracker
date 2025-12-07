package com.fridge.tracker.application.recipe.generate;

import com.fridge.tracker.application.recipe.generate.exception.NoAvailableIngredientsException;
import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import com.fridge.tracker.domain.recipe.RecipeGenerator;
import com.fridge.tracker.domain.recipe.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateRecipesHandler implements CommandHandler<GenerateRecipesCommand, List<Recipe>> {
    private final FridgeAccessService accessService;
    private final FridgeItemRepository repository;
    private final RecipeGenerator generator;

    @Override
    public List<Recipe> execute(GenerateRecipesCommand command) {
        String userId = command.getContext().getId();
        Long fridgeId = command.getFridgeId();

        if (!accessService.hasAccess(userId, fridgeId)) {
            throw new FridgeAccessDeniedException(userId, fridgeId);
        }

        List<String> ingredients = repository.getAvailableFridgeItems(fridgeId);
        if (ingredients.isEmpty()) {
            throw new NoAvailableIngredientsException();
        }

        return generator.generate(ingredients);
    }
}
