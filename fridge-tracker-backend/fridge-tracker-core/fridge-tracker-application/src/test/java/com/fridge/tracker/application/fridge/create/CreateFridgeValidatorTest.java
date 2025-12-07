package com.fridge.tracker.application.fridge.create;

import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.Validator;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fridge.tracker.application.fridge.create.CreateFridgeValidator.NAME_ALREADY_EXISTS;
import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static com.fridge.tracker.application.shared.utils.AssertionUtils.assertHasError;
import static com.fridge.tracker.application.shared.utils.AssertionUtils.assertValid;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateFridgeValidatorTest {
    @Mock
    FridgeRepository repository;

    Validator<CreateFridgeCommand> validator;

    @BeforeEach
    void initialize() {
        validator = new CreateFridgeValidator(repository);
    }

    @Test
    void validate_returnInvalidReport() {
        var command = mock(CreateFridgeCommand.class);

        when(command.getFridge()).thenReturn(new FridgeRecord("F"));
        when(command.getContext()).thenReturn(context);
        when(repository.exists(anyString(), anyString())).thenReturn(true);

        ValidationReport report = validator.validate(command);

        assertHasError(report, NAME_ALREADY_EXISTS);
    }

    @Test
    void validate_returnValidReport() {
        var command = mock(CreateFridgeCommand.class);

        when(command.getFridge()).thenReturn(new FridgeRecord("F"));
        when(command.getContext()).thenReturn(context);
        when(repository.exists(anyString(), anyString())).thenReturn(false);

        ValidationReport report = validator.validate(command);

        assertValid(report);
    }

}