package com.fridge.tracker.application.fridge_item.take;

import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.Validator;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.fridge.tracker.application.fridge_item.take.TakeFridgeItemValidator.INVALID_QUANTITY;
import static com.fridge.tracker.application.fridge_item.take.TakeFridgeItemValidator.NOT_ENOUGH_STOCK;
import static com.fridge.tracker.application.shared.utils.AssertionUtils.assertHasError;
import static com.fridge.tracker.application.shared.utils.AssertionUtils.assertValid;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TakeFridgeItemValidatorTest {
    @Mock
    FridgeItemRepository repository;

    Validator<TakeFridgeItemCommand> validator;

    @BeforeEach
    void initialize() {
        validator = new TakeFridgeItemValidator(repository);
    }

    @Test
    void validate_invalidQuantity_returnInvalidReport() {
        var command = mock(TakeFridgeItemCommand.class);

        when(command.getQuantity()).thenReturn(BigDecimal.ZERO);

        ValidationReport report = validator.validate(command);

        assertHasError(report, INVALID_QUANTITY);
    }

    @Test
    void validate_insufficientStock_returnInvalidReport() {
        var command = mock(TakeFridgeItemCommand.class);

        when(command.getFridgeItemId()).thenReturn(1L);
        when(command.getQuantity()).thenReturn(BigDecimal.TEN);
        when(repository.getQuantity(command.getFridgeItemId())).thenReturn(BigDecimal.TWO);

        ValidationReport report = validator.validate(command);

        assertHasError(report, NOT_ENOUGH_STOCK);
    }

    @Test
    void validate_valid_returnValidReport() {
        var command = mock(TakeFridgeItemCommand.class);

        when(command.getFridgeItemId()).thenReturn(1L);
        when(command.getQuantity()).thenReturn(BigDecimal.TEN);
        when(repository.getQuantity(command.getFridgeItemId())).thenReturn(BigDecimal.TEN);

        ValidationReport report = validator.validate(command);

        assertValid(report);
    }
}