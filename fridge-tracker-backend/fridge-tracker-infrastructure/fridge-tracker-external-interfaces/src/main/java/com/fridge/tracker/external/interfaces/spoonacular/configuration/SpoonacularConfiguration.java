package com.fridge.tracker.external.interfaces.spoonacular.configuration;

public interface SpoonacularConfiguration {
    String getBaseUrl();

    String getApiKey();

    int getResponseTimeoutSeconds();

    int getConnectionTimeoutMillis();
}
