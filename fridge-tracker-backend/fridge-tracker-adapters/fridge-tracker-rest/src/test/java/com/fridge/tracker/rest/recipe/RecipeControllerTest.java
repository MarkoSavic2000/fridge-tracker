package com.fridge.tracker.rest.recipe;

import com.fridge.tracker.application.recipe.generate.GenerateRecipesCommand;
import com.fridge.tracker.application.shared.cqrs.Bus;
import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.rest.RecipeApi;
import com.fridge.tracker.rest.model.GetRecipesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
    @Mock
    Bus bus;

    @Mock
    RecipeMapper mapper;

    RecipeApi controller;

    @BeforeEach
    void initialize() {
        controller = new RecipeController(bus, mapper);
    }

    @Test
    void getRecipes_returnResponse() {
        List<Recipe> result = List.of();
        var expectedResponse = new GetRecipesResponse();

        when(bus.execute(any(GenerateRecipesCommand.class))).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<GetRecipesResponse> response = controller.getRecipes(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}