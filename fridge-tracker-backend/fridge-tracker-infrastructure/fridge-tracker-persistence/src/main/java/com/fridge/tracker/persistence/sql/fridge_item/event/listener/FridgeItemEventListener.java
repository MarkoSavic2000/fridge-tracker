package com.fridge.tracker.persistence.sql.fridge_item.event.listener;

import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.persistence.sql.fridge.repository.FridgeJpaRepository;
import com.fridge.tracker.persistence.sql.fridge_item.event.entity.FridgeItemEventEntity;
import com.fridge.tracker.persistence.sql.fridge_item.event.mapper.FridgeItemEventEntityMapper;
import com.fridge.tracker.persistence.sql.fridge_item.event.repository.FridgeItemEventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class FridgeItemEventListener {
    private final FridgeItemEventJpaRepository repository;
    private final FridgeJpaRepository fridgeJpaRepository;
    private final FridgeItemEventEntityMapper mapper;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleFridgeItemEvent(FridgeItemEvent event) {
        FridgeItemEventEntity entity = mapper.map(event);

        if (isNull(entity.getFridgeId())) {
            Long fridgeId = fridgeJpaRepository.getId(entity.getFridgeItemId());
            entity.setFridgeId(fridgeId);
        }

        repository.save(entity);
    }
}
