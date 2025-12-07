package com.fridge.tracker.application.fridge.create;

import com.fridge.tracker.application.shared.validation.Message;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.Validator;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFridgeValidator implements Validator<CreateFridgeCommand> {
    static final Message NAME_ALREADY_EXISTS = new Message("name", "name.already.exists");

    private final FridgeRepository repository;

    @Override
    public ValidationReport validate(CreateFridgeCommand command) {
        String fridgeName = command.getFridge().getName();
        String userId = command.getContext().getId();

        return new ValidationReport()
                .rule(repository.exists(userId, fridgeName), NAME_ALREADY_EXISTS);
    }
}
