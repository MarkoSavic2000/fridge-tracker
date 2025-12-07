package com.fridge.tracker.domain.shared.query;

import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Filter that contains pagination data and sort column.
 * Can be base class for custom filter where pagination or sorting  is needed.
 *
 */
@Getter
@RequiredArgsConstructor
public class QueryFilter {
    private final int pageNumber;
    private final int pageSize;
    private final List<SortColumn> sortColumns;
}
