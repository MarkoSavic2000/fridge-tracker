package com.fridge.tracker.application.fridge_item.take;

import com.fridge.tracker.application.shared.cqrs.CommandHandler;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.application.shared.cqrs.result.UpdatedResult;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeItemAccessDeniedException;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.Validator;
import com.fridge.tracker.application.shared.validation.exception.ValidationException;
import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.fridge.tracker.application.fridge_item.take.TakeFridgeItemValidator.INVALID_QUANTITY;
import static com.fridge.tracker.domain.fridge_item.event.enumeration.FridgeItemEventTypeEnum.TAKE;

@Component
@RequiredArgsConstructor
public class TakeFridgeItemHandler implements CommandHandler<TakeFridgeItemCommand, Result<Long>> {
    private final Validator<TakeFridgeItemCommand> validator;
    private final FridgeAccessService accessService;
    private final FridgeItemRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public Result<Long> execute(TakeFridgeItemCommand command) {
        String userId = command.getContext().getId();
        Long fridgeItemId = command.getFridgeItemId();

        if (!accessService.hasFridgeItemAccess(userId, fridgeItemId)) {
            throw new FridgeItemAccessDeniedException(userId, fridgeItemId);
        }

        ValidationReport report = validator.validate(command);
        if (report.isInvalid()) {
            throw new ValidationException(report);
        }


        boolean success = repository.decreaseQuantity(fridgeItemId, command.getQuantity());
        if (!success) {
            throw new ValidationException(new ValidationReport(INVALID_QUANTITY));
        }

        publisher.publishEvent(new FridgeItemEvent(fridgeItemId, userId, TAKE, command.getQuantity()));

        return new UpdatedResult<>(fridgeItemId);
    }
}
