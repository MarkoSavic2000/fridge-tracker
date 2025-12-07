package com.fridge.tracker.application.fridge.delete;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.DeletedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteFridgeHandler implements CommandHandler<DeleteFridgeCommand, Result<Long>> {
    private final FridgeAccessService accessService;
    private final FridgeRepository repository;

    @Override
    @Transactional
    public Result<Long> execute(DeleteFridgeCommand command) {
        String userId = command.getContext().getId();
        Long fridgeId = command.getId();

        if (!accessService.hasAccess(userId, fridgeId)) {
            throw new FridgeAccessDeniedException(userId, fridgeId);
        }

        repository.delete(fridgeId);

        return new DeletedResult<>(fridgeId);
    }
}
