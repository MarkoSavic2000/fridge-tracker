package com.fridge.tracker.external.interfaces.spoonacular;

import com.fridge.tracker.domain.recipe.RecipeGenerator;
import com.fridge.tracker.domain.recipe.model.Recipe;
import com.fridge.tracker.external.interfaces.spoonacular.configuration.SpoonacularConfiguration;
import com.fridge.tracker.external.interfaces.spoonacular.mapper.SpoonacularRecipeMapper;
import com.fridge.tracker.external.interfaces.spoonacular.model.SpoonacularRecipe;
import io.netty.channel.ChannelOption;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;

import static com.fridge.tracker.external.interfaces.spoonacular.Spoonacular.API.FIND_RECIPES_BY_INGREDIENTS;
import static com.fridge.tracker.external.interfaces.spoonacular.Spoonacular.QueryParams.API_KEY;
import static com.fridge.tracker.external.interfaces.spoonacular.Spoonacular.QueryParams.INGREDIENTS;

@Component
public class SpoonacularRecipeGenerator implements RecipeGenerator {
    private final SpoonacularConfiguration configuration;
    private final WebClient client;
    private final SpoonacularRecipeMapper mapper;

    public SpoonacularRecipeGenerator(SpoonacularConfiguration configuration, WebClient.Builder webClientBuilder,
                                      SpoonacularRecipeMapper mapper) {
        this.configuration = configuration;
        this.client = webClientBuilder
                .baseUrl(configuration.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(configuration.getResponseTimeoutSeconds()))
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, configuration.getConnectionTimeoutMillis())))
                .build();
        this.mapper = mapper;
    }

    @Override
    public List<Recipe> generate(List<String> ingredients) {
        String ingredientsInput = String.join(",", ingredients);

        List<SpoonacularRecipe> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(FIND_RECIPES_BY_INGREDIENTS)
                        .queryParam(INGREDIENTS, ingredientsInput)
                        .queryParam(API_KEY, configuration.getApiKey())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SpoonacularRecipe>>() {
                })
                .block();

        return mapper.map(response);
    }
}
