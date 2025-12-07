package com.fridge.tracker.application.recipe.generate;

import com.fridge.tracker.application.recipe.generate.exception.NoAvailableIngredientsException;
import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import com.fridge.tracker.domain.recipe.RecipeGenerator;
import com.fridge.tracker.domain.recipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateRecipesHandlerTest {
    @Mock
    FridgeAccessService accessService;

    @Mock
    FridgeItemRepository repository;

    @Mock
    RecipeGenerator generator;

    CommandHandler<GenerateRecipesCommand, List<Recipe>> handler;

    @BeforeEach
    void initialize() {
        handler = new GenerateRecipesHandler(accessService, repository, generator);
    }

    @Test
    void execute_accessForbidden_throwFridgeAccessDeniedException() {
        var command = mock(GenerateRecipesCommand.class);

        when(command.getContext()).thenReturn(context);
        when(command.getFridgeId()).thenReturn(1L);
        when(accessService.hasAccess(context.getId(), command.getFridgeId())).thenReturn(false);

        assertThrows(FridgeAccessDeniedException.class, () -> handler.execute(command));
    }

    @Test
    void execute_noIngredients_throwNoAvailableIngredientsException() {
        var command = mock(GenerateRecipesCommand.class);

        when(command.getContext()).thenReturn(context);
        when(command.getFridgeId()).thenReturn(1L);
        when(accessService.hasAccess(context.getId(), command.getFridgeId())).thenReturn(true);
        when(repository.getAvailableFridgeItems(command.getFridgeId())).thenReturn(List.of());

        assertThrows(NoAvailableIngredientsException.class, () -> handler.execute(command));
    }

    @Test
    void execute_returnResult() {
        var command = mock(GenerateRecipesCommand.class);
        List<String> ingredients = List.of("milk");

        when(command.getContext()).thenReturn(context);
        when(command.getFridgeId()).thenReturn(1L);
        when(accessService.hasAccess(context.getId(), command.getFridgeId())).thenReturn(true);
        when(repository.getAvailableFridgeItems(command.getFridgeId())).thenReturn(ingredients);

        List<Recipe> result = handler.execute(command);

        assertNotNull(result);
        verify(generator).generate(ingredients);
    }
}