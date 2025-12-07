package com.fridge.tracker.domain.fridge_item.model.enumeration;

/**
 * For now fridge item category is represented as enum. In the future item categories can become an entity,
 * and then we can allow users to create their own custom categories.
 */
public enum FridgeItemCategoryEnum {
    DAIRY,
    MEAT,
    FISH,
    VEGETABLES,
    FRUITS,
    BEVERAGES,
    BAKERY,
    FROZEN,
    CONDIMENTS,
    SNACKS,
    HERBS_SPICES,
    READY_MEALS,
    OTHER
}
