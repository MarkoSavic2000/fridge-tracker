package com.fridge.tracker.application.fridge_item.list;

import com.fridge.tracker.application.shared.cqrs.Query;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Query to retrieve fridge items from the fridge.
 */
@Getter
@RequiredArgsConstructor
public class ListFridgeItemsQuery extends Query {
    private final FridgeItemQueryFilter filter;
}
