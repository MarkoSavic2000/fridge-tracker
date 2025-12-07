package com.fridge.tracker.domain.fridge.model;

import com.fridge.tracker.domain.shared.query.Page;
import lombok.Getter;

import java.util.List;

@Getter
public class FridgePage extends Page {
    private final List<FridgeDetails> fridges;

    public FridgePage(int pageNumber, int pageSize, int totalPages, long totalElements, boolean hasNext,
                      boolean hasPrevious, List<FridgeDetails> fridges) {
        super(pageNumber, pageSize, totalPages, totalElements, hasNext, hasPrevious);
        this.fridges = fridges;
    }
}
