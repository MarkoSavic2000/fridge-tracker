package com.fridge.tracker.application.fridge_item.add;

import com.fridge.tracker.application.shared.cqrs.Command;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Command that adds fridge item to the fridge
 */
@Getter
@RequiredArgsConstructor
public class AddFridgeItemCommand extends Command {
    private final FridgeItemRecord fridgeItem;
}
