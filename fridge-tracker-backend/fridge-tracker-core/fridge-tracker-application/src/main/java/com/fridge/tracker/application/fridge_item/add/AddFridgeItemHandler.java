package com.fridge.tracker.application.fridge_item.add;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.fridge.tracker.domain.fridge_item.event.enumeration.FridgeItemEventTypeEnum.ADD;

@Component
@RequiredArgsConstructor
public class AddFridgeItemHandler implements CommandHandler<AddFridgeItemCommand, Result<FridgeItemRecord>> {
    private final FridgeAccessService accessService;
    private final FridgeItemRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public Result<FridgeItemRecord> execute(AddFridgeItemCommand command) {
        String userId = command.getContext().getId();
        FridgeItemRecord fridgeItem = command.getFridgeItem();
        Long fridgeId = fridgeItem.getFridgeId();

        if (!accessService.hasAccess(userId, fridgeId)) {
            throw new FridgeAccessDeniedException(userId, fridgeId);
        }

        Long fridgeItemId = repository.save(fridgeItem);

        publisher.publishEvent(new FridgeItemEvent(fridgeItemId, fridgeId, userId, ADD, fridgeItem.getQuantity()));

        return new CreatedResult<>(fridgeItem);
    }
}
