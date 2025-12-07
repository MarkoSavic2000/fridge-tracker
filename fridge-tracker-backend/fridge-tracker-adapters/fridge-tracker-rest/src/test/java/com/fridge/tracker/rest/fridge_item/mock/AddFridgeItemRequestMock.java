package com.fridge.tracker.rest.fridge_item.mock;

import com.fridge.tracker.rest.model.AddFridgeItemRequest;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fridge.tracker.rest.model.FridgeItemCategoryEnumApi.BEVERAGES;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class AddFridgeItemRequestMock {

    public static AddFridgeItemRequest request = new AddFridgeItemRequest()
            .name("name")
            .quantity(BigDecimal.valueOf(200.5))
            .measurementUnit("l")
            .category(BEVERAGES)
            .storedOn(LocalDateTime.of(2022, 2, 2, 2, 2))
            .expiresOn(LocalDate.of(2023, 2, 2))
            .note("note");
}
