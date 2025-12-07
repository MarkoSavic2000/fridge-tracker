package com.fridge.tracker.persistence.sql.fridge.mapper;

import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.persistence.sql.fridge.entity.FridgeEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FridgeEntityMapper {

    FridgeEntity map(FridgeRecord fridge);
}
