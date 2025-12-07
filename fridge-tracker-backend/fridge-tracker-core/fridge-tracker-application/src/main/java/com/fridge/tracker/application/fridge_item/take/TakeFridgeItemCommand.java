package com.fridge.tracker.application.fridge_item.take;

import com.fridge.tracker.application.shared.cqrs.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * Command that takes given quantity of fridge item.
 */
@Getter
@RequiredArgsConstructor
public class TakeFridgeItemCommand extends Command {
    private final Long fridgeItemId;
    private final BigDecimal quantity;
}
