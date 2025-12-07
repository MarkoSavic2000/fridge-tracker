package com.fridge.tracker.application.fridge.create;

import com.fridge.tracker.application.shared.context.Context;
import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.exception.ValidationException;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.domain.user_account.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateFridgeHandler implements CommandHandler<CreateFridgeCommand, Result<FridgeRecord>> {
    private final CreateFridgeValidator validator;
    private final UserAccountRepository userAccountRepository;
    private final FridgeRepository fridgeRepository;

    @Override
    @Transactional
    public Result<FridgeRecord> execute(CreateFridgeCommand command) {
        ValidationReport report = validator.validate(command);
        if (report.isInvalid()) {
            throw new ValidationException(report);
        }

        Context context = command.getContext();
        String userId = context.getId();
        FridgeRecord fridge = command.getFridge();

        UserAccount userAccount = userAccountRepository.get(userId)
                .orElseGet(() -> create(userId, context.getUsername()));

        fridge.setUserAccountId(userAccount.getId());
        fridgeRepository.save(fridge);

        return new CreatedResult<>(fridge);
    }

    private UserAccount create(String userId, String username) {
        var userAccount = new UserAccount(username, userId);
        return userAccountRepository.save(userAccount);
    }
}
