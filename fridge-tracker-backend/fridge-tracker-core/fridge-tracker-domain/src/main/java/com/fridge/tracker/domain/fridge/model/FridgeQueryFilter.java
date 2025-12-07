package com.fridge.tracker.domain.fridge.model;

import com.fridge.tracker.domain.shared.query.QueryFilter;
import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import lombok.Getter;

import java.util.List;

/**
 * Filters fridges based on provided data.
 */
@Getter
public class FridgeQueryFilter extends QueryFilter {
    private final String name;

    public FridgeQueryFilter(int pageNumber, int pageSize, List<SortColumn> sortColumns, String name) {
        super(pageNumber, pageSize, sortColumns);
        this.name = name;
    }

}
