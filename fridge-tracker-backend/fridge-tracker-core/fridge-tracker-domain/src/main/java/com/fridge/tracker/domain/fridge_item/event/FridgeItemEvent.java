package com.fridge.tracker.domain.fridge_item.event;

import com.fridge.tracker.domain.fridge_item.event.enumeration.FridgeItemEventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class FridgeItemEvent {
    private Long fridgeItemId;
    private Long fridgeId;
    private String userId;
    private FridgeItemEventTypeEnum type;
    private BigDecimal quantity;

    public FridgeItemEvent(Long fridgeItemId, String userId, FridgeItemEventTypeEnum type, BigDecimal quantity) {
        this.fridgeItemId = fridgeItemId;
        this.userId = userId;
        this.type = type;
        this.quantity = quantity;
    }

    public FridgeItemEvent(Long fridgeItemId, String userId, FridgeItemEventTypeEnum type) {
        this.fridgeItemId = fridgeItemId;
        this.userId = userId;
        this.type = type;
    }
}
