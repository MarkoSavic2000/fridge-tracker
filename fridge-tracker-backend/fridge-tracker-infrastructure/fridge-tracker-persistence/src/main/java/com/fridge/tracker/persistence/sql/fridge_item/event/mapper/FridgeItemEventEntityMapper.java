package com.fridge.tracker.persistence.sql.fridge_item.event.mapper;

import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.persistence.sql.fridge_item.event.entity.FridgeItemEventEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FridgeItemEventEntityMapper {

    FridgeItemEventEntity map(FridgeItemEvent event);
}
