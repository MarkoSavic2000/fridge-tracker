package com.fridge.tracker.application.shared.security.exception;

/**
 * Exception thrown when a user tries to access a fridge item which is not allowed to.
 */
public class FridgeItemAccessDeniedException extends RuntimeException {
    private static final String ERROR_MESSAGE = "User with id %s tried to access fridge item with id %d";

    private final String userId;
    private final Long fridgeItemId;

    public FridgeItemAccessDeniedException(String userId, Long fridgeItemId) {
        super("Access denied!");
        this.userId = userId;
        this.fridgeItemId = fridgeItemId;
    }

    public String getError() {
        return String.format(ERROR_MESSAGE, userId, fridgeItemId);
    }
}
