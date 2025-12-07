package com.fridge.tracker.application.fridge.create;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.validation.exception.ValidationException;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.domain.user_account.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static com.fridge.tracker.application.shared.mock.ValidationReportMock.invalidReport;
import static com.fridge.tracker.application.shared.mock.ValidationReportMock.validReport;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFridgeHandlerTest {
    @Mock
    CreateFridgeValidator validator;

    @Mock
    UserAccountRepository userAccountRepository;

    @Mock
    FridgeRepository fridgeRepository;

    CommandHandler<CreateFridgeCommand, Result<FridgeRecord>> handler;

    @BeforeEach
    void initialize() {
        handler = new CreateFridgeHandler(validator, userAccountRepository, fridgeRepository);
    }

    @Test
    void execute_invalidCommand_throwValidationException() {
        var command = mock(CreateFridgeCommand.class);
        when(validator.validate(any(CreateFridgeCommand.class))).thenReturn(invalidReport);

        assertThrows(ValidationException.class, () -> handler.execute(command));
    }

    @Test
    void execute_userAccountExists_returnCreatedResult() {
        var command = mock(CreateFridgeCommand.class);

        when(validator.validate(command)).thenReturn(validReport);
        when(command.getFridge()).thenReturn(new FridgeRecord("F1"));
        when(command.getContext()).thenReturn(context);
        when(userAccountRepository.get(context.getId())).thenReturn(Optional.of(new UserAccount("email", "1")));

        Result<FridgeRecord> result = handler.execute(command);

        assertNotNull(result);
        assertInstanceOf(CreatedResult.class, result);

        verify(fridgeRepository).save(any(FridgeRecord.class));
        verify(userAccountRepository).get(context.getId());
        verifyNoMoreInteractions(userAccountRepository);
    }

    @Test
    void execute_userAccountDoesNotExist_returnCreatedResult() {
        var command = mock(CreateFridgeCommand.class);

        when(validator.validate(command)).thenReturn(validReport);
        when(command.getFridge()).thenReturn(new FridgeRecord("F1"));
        when(command.getContext()).thenReturn(context);
        when(userAccountRepository.get(context.getId())).thenReturn(Optional.empty());
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(new UserAccount("email", "1"));

        Result<FridgeRecord> result = handler.execute(command);

        assertNotNull(result);
        assertInstanceOf(CreatedResult.class, result);

        verify(fridgeRepository).save(any(FridgeRecord.class));
    }
}