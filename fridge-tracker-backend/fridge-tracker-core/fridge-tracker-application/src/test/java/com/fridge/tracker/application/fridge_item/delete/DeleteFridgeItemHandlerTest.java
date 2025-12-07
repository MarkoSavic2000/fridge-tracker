package com.fridge.tracker.application.fridge_item.delete;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.DeletedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeItemAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFridgeItemHandlerTest {
    @Mock
    FridgeAccessService accessService;

    @Mock
    FridgeItemRepository repository;

    @Mock
    ApplicationEventPublisher publisher;

    CommandHandler<DeleteFridgeItemCommand, Result<Long>> handler;

    @BeforeEach
    void initialize() {
        handler = new DeleteFridgeItemHandler(accessService, repository, publisher);
    }

    @Test
    void execute_accessForbidden_throwFridgeItemAccessDeniedException() {
        var command = mock(DeleteFridgeItemCommand.class);

        when(command.getId()).thenReturn(1L);
        when(command.getContext()).thenReturn(context);
        when(accessService.hasFridgeItemAccess(anyString(), anyLong())).thenReturn(false);

        assertThrows(FridgeItemAccessDeniedException.class, () -> handler.execute(command));
    }

    @Test
    void execute_returnDeletedResult() {
        var command = mock(DeleteFridgeItemCommand.class);

        when(command.getId()).thenReturn(1L);
        when(command.getContext()).thenReturn(context);
        when(accessService.hasFridgeItemAccess(anyString(), anyLong())).thenReturn(true);

        Result<Long> result = handler.execute(command);

        assertNotNull(result);
        assertInstanceOf(DeletedResult.class, result);
        verify(repository).delete(anyLong());
        verify(publisher).publishEvent(any(FridgeItemEvent.class));
    }
}