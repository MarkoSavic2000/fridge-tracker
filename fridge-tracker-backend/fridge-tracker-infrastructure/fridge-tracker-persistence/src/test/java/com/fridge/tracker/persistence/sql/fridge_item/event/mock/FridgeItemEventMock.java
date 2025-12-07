package com.fridge.tracker.persistence.sql.fridge_item.event.mock;

import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fridge.tracker.domain.fridge_item.event.enumeration.FridgeItemEventTypeEnum.ADD;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class FridgeItemEventMock {
    public static FridgeItemEvent event = new FridgeItemEvent(1L, 12L, "12", ADD, BigDecimal.ONE);
}
