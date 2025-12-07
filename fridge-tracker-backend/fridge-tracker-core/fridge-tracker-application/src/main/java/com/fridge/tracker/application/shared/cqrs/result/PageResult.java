package com.fridge.tracker.application.shared.cqrs.result;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResult<T> extends Result<List<T>> {
    private final int pageSize;
    private final int pageNumber;
    private final int totalPages;
    private final long totalElements;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public PageResult(List<T> value, int pageSize, int pageNumber, int totalPages, long totalElements, boolean hasNext, boolean hasPrevious) {
        super(value);
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}
