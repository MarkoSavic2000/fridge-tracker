package com.fridge.tracker.application.shared.cqrs.exception;

/**
 * Exception thrown when handler is not found for given command or query.
 */
public class HandlerNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Handler not found for %s.";

    public HandlerNotFoundException(Class<?> input) {
        super(String.format(MESSAGE, input.getSimpleName()));
    }
}
