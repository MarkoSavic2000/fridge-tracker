package com.fridge.tracker.application.fridge_item.add;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddFridgeItemHandlerTest {
    @Mock
    FridgeAccessService accessService;

    @Mock
    FridgeItemRepository repository;

    @Mock
    ApplicationEventPublisher publisher;

    CommandHandler<AddFridgeItemCommand, Result<FridgeItemRecord>> handler;

    @BeforeEach
    void initialize() {
        handler = new AddFridgeItemHandler(accessService, repository, publisher);
    }

    @Test
    void execute_forbiddenAccess_throwFridgeAccessDeniedException() {
        var command = mock(AddFridgeItemCommand.class);
        var fridgeItem = mock(FridgeItemRecord.class);
        long fridgeId = 1;

        when(command.getContext()).thenReturn(context);
        when(command.getFridgeItem()).thenReturn(fridgeItem);
        when(fridgeItem.getFridgeId()).thenReturn(fridgeId);
        when(accessService.hasAccess(context.getId(), fridgeId)).thenReturn(false);

        assertThrows(FridgeAccessDeniedException.class, () -> handler.execute(command));
    }

    @Test
    void execute_returnCreatedResult() {
        var command = mock(AddFridgeItemCommand.class);
        var fridgeItem = mock(FridgeItemRecord.class);
        long fridgeId = 1;

        when(command.getContext()).thenReturn(context);
        when(command.getFridgeItem()).thenReturn(fridgeItem);
        when(fridgeItem.getFridgeId()).thenReturn(fridgeId);
        when(accessService.hasAccess(context.getId(), fridgeId)).thenReturn(true);
        when(repository.save(fridgeItem)).thenReturn(1L);

        Result<FridgeItemRecord> result = handler.execute(command);

        assertNotNull(result);
        assertInstanceOf(CreatedResult.class, result);
        verify(publisher).publishEvent(any(FridgeItemEvent.class));
    }
}