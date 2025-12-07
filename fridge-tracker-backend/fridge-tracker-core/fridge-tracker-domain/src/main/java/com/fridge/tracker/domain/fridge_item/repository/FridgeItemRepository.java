package com.fridge.tracker.domain.fridge_item.repository;

import com.fridge.tracker.domain.fridge_item.model.FridgeItemPage;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;

import java.math.BigDecimal;
import java.util.List;

public interface FridgeItemRepository {

    /**
     * Saves fridge item.
     *
     * @param fridgeItem fridge item to save
     * @return unique identfier of saved fridge item
     */
    Long save(FridgeItemRecord fridgeItem);

    /**
     * Deletes fridge item with given ID.
     *
     * @param id unique identifier of the fridge item
     */
    void delete(Long id);

    /**
     * Gets quantity of fridge item with given ID.
     *
     * @param id unique identifier of the fridge item
     * @return current quantity of fridge item
     */
    BigDecimal getQuantity(Long id);

    /**
     * Decreases quantity of fridge item for the given amount.
     *
     * @param fridgeItemId unique identifier of the fridge item
     * @param amount       amount for which current quantity will be decreased
     * @return {@code true} if decrease is performed successfully; otherwise {@code false}
     */
    boolean decreaseQuantity(Long fridgeItemId, BigDecimal amount);

    /**
     * Retrieves fridge items based on the given filter.
     *
     * @param filter contains filter data
     * @return {@link FridgeItemPage}
     */
    FridgeItemPage get(FridgeItemQueryFilter filter);

    /**
     * Gets fridge items that can be used from the fridge with given ID.
     *
     * @param fridgeId unique identifier of the fridge
     * @return list of fridge item names
     */
    List<String> getAvailableFridgeItems(Long fridgeId);
}
