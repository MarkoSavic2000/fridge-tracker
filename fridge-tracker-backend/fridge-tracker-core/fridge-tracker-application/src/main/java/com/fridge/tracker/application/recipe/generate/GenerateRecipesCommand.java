package com.fridge.tracker.application.recipe.generate;

import com.fridge.tracker.application.shared.cqrs.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GenerateRecipesCommand extends Command {
    private final Long fridgeId;
}
