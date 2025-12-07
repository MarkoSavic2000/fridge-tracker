package com.fridge.tracker.application.fridge_item.delete;

import com.fridge.tracker.application.shared.cqrs.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Command that deletes fridge item with given ID.
 */
@Getter
@RequiredArgsConstructor
public class DeleteFridgeItemCommand extends Command {
    private final Long id;
}
