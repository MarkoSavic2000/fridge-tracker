package com.fridge.tracker.external.interfaces.spoonacular.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spoonacular")
public class SpoonacularConfigurationImpl implements SpoonacularConfiguration {
    private String baseUrl;
    private String apiKey;
    private int responseTimeoutSeconds;
    private int connectionTimeoutMillis;
}
