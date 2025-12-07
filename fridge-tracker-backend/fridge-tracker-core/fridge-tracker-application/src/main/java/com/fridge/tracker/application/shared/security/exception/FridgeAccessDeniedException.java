package com.fridge.tracker.application.shared.security.exception;

/**
 * Exception thrown when a user tries to access a fridge which is not allowed to.
 */
public class FridgeAccessDeniedException extends RuntimeException {
    private static final String ERROR_MESSAGE = "User with id %s tried to access fridge with id %d";

    private final String userId;
    private final Long fridgeId;

    public FridgeAccessDeniedException(String userId, Long fridgeId) {
        super("Access denied!");
        this.userId = userId;
        this.fridgeId = fridgeId;
    }

    public String getError() {
        return String.format(ERROR_MESSAGE, userId, fridgeId);
    }
}
