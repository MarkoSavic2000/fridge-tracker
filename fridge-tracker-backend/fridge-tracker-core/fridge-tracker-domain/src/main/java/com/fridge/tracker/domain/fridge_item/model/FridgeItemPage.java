package com.fridge.tracker.domain.fridge_item.model;

import com.fridge.tracker.domain.shared.query.Page;
import lombok.Getter;

import java.util.List;

@Getter
public class FridgeItemPage extends Page {
    private final List<FridgeItemDetails> fridgeItems;

    public FridgeItemPage(int pageNumber, int pageSize, int totalPages, long totalElements, boolean hasNext,
                          boolean hasPrevious, List<FridgeItemDetails> fridgeItems) {
        super(pageNumber, pageSize, totalPages, totalElements, hasNext, hasPrevious);
        this.fridgeItems = fridgeItems;
    }
}
