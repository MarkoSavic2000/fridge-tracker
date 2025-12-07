package com.fridge.tracker.application.shared.security;

/**
 * Service to check if a user has access to a contract.
 */
public interface FridgeAccessService {

    /**
     * Checks if the user has access to the fridge.
     *
     * @param userId   unique identifier of the user
     * @param fridgeId unique identifier of the fridge
     * @return {@code true} if has access; otherwise {@code false}
     */
    boolean hasAccess(String userId, Long fridgeId);

    /**
     * Checks if the user has access to the fridge item.
     *
     * @param userId       unique identifier of the user
     * @param fridgeItemId unique identifier of the fridge item
     * @return {@code true} if has access; otherwise {@code false}
     */
    boolean hasFridgeItemAccess(String userId, Long fridgeItemId);
}
