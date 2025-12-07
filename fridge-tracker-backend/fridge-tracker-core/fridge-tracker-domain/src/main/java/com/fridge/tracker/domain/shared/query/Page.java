package com.fridge.tracker.domain.shared.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base class for any custom page.
 */
@Getter
@AllArgsConstructor
public abstract class Page {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;
}
