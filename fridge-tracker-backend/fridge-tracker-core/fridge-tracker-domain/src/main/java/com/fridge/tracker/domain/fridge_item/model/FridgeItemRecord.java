package com.fridge.tracker.domain.fridge_item.model;

import com.fridge.tracker.domain.fridge_item.model.enumeration.FridgeItemCategoryEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FridgeItemRecord {
    private Long fridgeId;
    private String name;
    private BigDecimal quantity;
    private String measurementUnit;
    private FridgeItemCategoryEnum category;
    private LocalDateTime storedOn;
    private LocalDate expiresOn;
    private String note;
}
