package com.fridge.tracker.application.fridge_item.take;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.cqrs.result.UpdatedResult;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeItemAccessDeniedException;
import com.fridge.tracker.application.shared.validation.Validator;
import com.fridge.tracker.application.shared.validation.exception.ValidationException;
import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static com.fridge.tracker.application.shared.mock.ValidationReportMock.invalidReport;
import static com.fridge.tracker.application.shared.mock.ValidationReportMock.validReport;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TakeFridgeItemHandlerTest {
    @Mock
    Validator<TakeFridgeItemCommand> validator;

    @Mock
    FridgeAccessService accessService;

    @Mock
    FridgeItemRepository repository;

    @Mock
    TakeFridgeItemCommand command;

    @Mock
    ApplicationEventPublisher publisher;

    CommandHandler<TakeFridgeItemCommand, Result<Long>> handler;

    @BeforeEach
    void initialize() {
        when(command.getContext()).thenReturn(context);
        when(command.getFridgeItemId()).thenReturn(1L);

        handler = new TakeFridgeItemHandler(validator, accessService, repository, publisher);
    }

    @Test
    void execute_forbiddenAccess_throwFridgeItemAccessDeniedException() {
        when(accessService.hasFridgeItemAccess(context.getId(), command.getFridgeItemId())).thenReturn(false);

        assertThrows(FridgeItemAccessDeniedException.class, () -> handler.execute(command));
    }

    @Test
    void execute_invalidCommand_throwValidationException() {
        when(accessService.hasFridgeItemAccess(context.getId(), command.getFridgeItemId())).thenReturn(true);
        when(validator.validate(command)).thenReturn(invalidReport);

        assertThrows(ValidationException.class, () -> handler.execute(command));
    }

    @Test
    void execute_insufficientStock_throwValidationException() {
        when(command.getQuantity()).thenReturn(BigDecimal.valueOf(100));
        when(accessService.hasFridgeItemAccess(context.getId(), command.getFridgeItemId())).thenReturn(true);
        when(validator.validate(command)).thenReturn(validReport);
        when(repository.decreaseQuantity(command.getFridgeItemId(), command.getQuantity())).thenReturn(false);

        assertThrows(ValidationException.class, () -> handler.execute(command));
    }

    @Test
    void execute_returnUpdatedResult() {
        when(command.getQuantity()).thenReturn(BigDecimal.valueOf(100));
        when(accessService.hasFridgeItemAccess(context.getId(), command.getFridgeItemId())).thenReturn(true);
        when(validator.validate(command)).thenReturn(validReport);
        when(repository.decreaseQuantity(command.getFridgeItemId(), command.getQuantity())).thenReturn(true);

        Result<Long> result = handler.execute(command);

        assertNotNull(result);
        assertInstanceOf(UpdatedResult.class, result);
        verify(publisher).publishEvent(any(FridgeItemEvent.class));
    }

}