package com.fridge.tracker.application.fridge.delete;

import com.fridge.tracker.application.shared.cqrs.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Command to delete a fridge with given ID.
 */
@Getter
@RequiredArgsConstructor
public class DeleteFridgeCommand extends Command {
    private final Long id;
}
