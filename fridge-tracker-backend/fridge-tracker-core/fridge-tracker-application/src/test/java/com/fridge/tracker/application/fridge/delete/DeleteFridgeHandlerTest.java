package com.fridge.tracker.application.fridge.delete;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.DeletedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFridgeHandlerTest {
    @Mock
    FridgeAccessService accessService;

    @Mock
    FridgeRepository repository;

    CommandHandler<DeleteFridgeCommand, Result<Long>> handler;

    @BeforeEach
    void initialize() {
        handler = new DeleteFridgeHandler(accessService, repository);
    }

    @Test
    void execute_forbiddenAccess_throwFridgeAccessDeniedException() {
        var command = mock(DeleteFridgeCommand.class);

        when(command.getContext()).thenReturn(context);
        when(accessService.hasAccess(anyString(), anyLong())).thenReturn(false);

        assertThrows(FridgeAccessDeniedException.class, () -> handler.execute(command));
    }

    @Test
    void execute_returnDeletedResult() {
        var command = mock(DeleteFridgeCommand.class);

        when(command.getContext()).thenReturn(context);
        when(accessService.hasAccess(anyString(), anyLong())).thenReturn(true);

        Result<Long> result = handler.execute(command);

        assertNotNull(result);
        assertInstanceOf(DeletedResult.class, result);
        verify(repository).delete(command.getId());
    }

}