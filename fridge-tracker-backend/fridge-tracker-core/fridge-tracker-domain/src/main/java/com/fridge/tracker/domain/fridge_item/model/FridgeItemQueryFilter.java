package com.fridge.tracker.domain.fridge_item.model;

import com.fridge.tracker.domain.fridge_item.model.enumeration.FridgeItemCategoryEnum;
import com.fridge.tracker.domain.shared.query.QueryFilter;
import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Filters fridge items based on provided data.
 */
@Getter
public class FridgeItemQueryFilter extends QueryFilter {
    private final Long fridgeId;
    private final String name;
    private final FridgeItemCategoryEnum category;
    private final LocalDate storedOnFrom;
    private final LocalDate storedOnTo;
    private final LocalDate expiresOnFrom;
    private final LocalDate expiresOnTo;
    private final Boolean onlyExpired;

    public FridgeItemQueryFilter(int pageNumber, int pageSize, List<SortColumn> sortColumns, Long fridgeId,
                                 String name, FridgeItemCategoryEnum category, LocalDate storedOnFrom, LocalDate storedOnTo,
                                 LocalDate expiresOnFrom, LocalDate expiresOnTo, Boolean onlyExpired) {
        super(pageNumber, pageSize, sortColumns);
        this.fridgeId = fridgeId;
        this.name = name;
        this.category = category;
        this.storedOnFrom = storedOnFrom;
        this.storedOnTo = storedOnTo;
        this.expiresOnFrom = expiresOnFrom;
        this.expiresOnTo = expiresOnTo;
        this.onlyExpired = onlyExpired;
    }

    public boolean isNameProvided() {
        return nonNull(name) && !name.isEmpty();
    }

    public boolean isCategoryProvided() {
        return nonNull(category);
    }

    public boolean isStoredOnFromProvided() {
        return nonNull(storedOnFrom);
    }

    public boolean isStoredOnToProvided() {
        return nonNull(storedOnTo);
    }

    public boolean isExpiresOnFromProvided() {
        return nonNull(expiresOnFrom);
    }

    public boolean isExpiresOnToProvided() {
        return nonNull(expiresOnTo);
    }

    public boolean retrieveOnlyExpired() {
        return Boolean.TRUE.equals(onlyExpired);
    }
}
