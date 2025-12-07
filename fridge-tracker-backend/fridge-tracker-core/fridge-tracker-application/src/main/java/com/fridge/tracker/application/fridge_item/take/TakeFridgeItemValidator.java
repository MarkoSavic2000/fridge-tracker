package com.fridge.tracker.application.fridge_item.take;

import com.fridge.tracker.application.shared.validation.Message;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.Validator;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TakeFridgeItemValidator implements Validator<TakeFridgeItemCommand> {
    static final Message INVALID_QUANTITY = new Message("quantity", "invalid");
    static final Message NOT_ENOUGH_STOCK = new Message("quantity", "not.enough.stock");

    private final FridgeItemRepository repository;

    @Override
    public ValidationReport validate(TakeFridgeItemCommand command) {
        BigDecimal quantity = command.getQuantity();

        return new ValidationReport()
                .rule(quantity.compareTo(BigDecimal.ZERO) <= 0, INVALID_QUANTITY)
                .ifValidRule(() -> isStockInsufficient(command.getFridgeItemId(), quantity), NOT_ENOUGH_STOCK);
    }

    private boolean isStockInsufficient(Long fridgeItemId, BigDecimal quantity) {
        BigDecimal currentQuantity = repository.getQuantity(fridgeItemId);
        return quantity.compareTo(currentQuantity) > 0;
    }

}
