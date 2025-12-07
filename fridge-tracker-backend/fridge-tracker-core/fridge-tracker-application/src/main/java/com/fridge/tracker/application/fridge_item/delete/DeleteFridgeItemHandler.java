package com.fridge.tracker.application.fridge_item.delete;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.DeletedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeItemAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.fridge.tracker.domain.fridge_item.event.enumeration.FridgeItemEventTypeEnum.DELETE;

@Component
@RequiredArgsConstructor
public class DeleteFridgeItemHandler implements CommandHandler<DeleteFridgeItemCommand, Result<Long>> {
    private final FridgeAccessService accessService;
    private final FridgeItemRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public Result<Long> execute(DeleteFridgeItemCommand command) {
        String userId = command.getContext().getId();
        Long fridgeItemId = command.getId();

        if (!accessService.hasFridgeItemAccess(userId, fridgeItemId)) {
            throw new FridgeItemAccessDeniedException(userId, fridgeItemId);
        }

        repository.delete(fridgeItemId);

        publisher.publishEvent(new FridgeItemEvent(fridgeItemId, userId, DELETE));

        return new DeletedResult<>(fridgeItemId);
    }
}
