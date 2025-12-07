package com.fridge.tracker.application.recipe.generate.exception;

/**
 * Exception thrown when there are no available ingredients for the recipe generation in the fridge.
 */
public class NoAvailableIngredientsException extends RuntimeException {
    private static final String MESSAGE = "no.available.ingredients";

    public NoAvailableIngredientsException() {
        super(MESSAGE);
    }
}
