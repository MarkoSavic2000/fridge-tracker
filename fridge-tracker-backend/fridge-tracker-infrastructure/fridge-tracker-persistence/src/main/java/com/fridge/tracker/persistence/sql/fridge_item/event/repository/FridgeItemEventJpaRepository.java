package com.fridge.tracker.persistence.sql.fridge_item.event.repository;

import com.fridge.tracker.persistence.sql.fridge_item.event.entity.FridgeItemEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeItemEventJpaRepository extends JpaRepository<FridgeItemEventEntity, Long> {
}
