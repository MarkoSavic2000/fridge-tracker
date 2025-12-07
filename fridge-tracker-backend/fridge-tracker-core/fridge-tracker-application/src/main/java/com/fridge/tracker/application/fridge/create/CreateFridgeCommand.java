package com.fridge.tracker.application.fridge.create;

import com.fridge.tracker.application.shared.cqrs.Command;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Command to create fridge.
 */
@Getter
@RequiredArgsConstructor
public class CreateFridgeCommand extends Command {
    private final FridgeRecord fridge;
}
