package com.fridge.tracker.application.fridge.list;

import com.fridge.tracker.application.shared.cqrs.Query;
import com.fridge.tracker.domain.fridge.model.FridgeQueryFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Query to retrieve fridges for the current user.
 */
@Getter
@RequiredArgsConstructor
public class ListFridgesQuery extends Query {
    private final FridgeQueryFilter filter;
}
