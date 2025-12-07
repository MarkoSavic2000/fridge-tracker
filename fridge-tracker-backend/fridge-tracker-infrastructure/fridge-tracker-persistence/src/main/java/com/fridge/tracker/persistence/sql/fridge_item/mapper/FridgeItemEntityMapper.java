package com.fridge.tracker.persistence.sql.fridge_item.mapper;

import com.fridge.tracker.domain.fridge_item.model.FridgeItemDetails;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FridgeItemEntityMapper {

    FridgeItemEntity map(FridgeItemRecord fridgeItem);

    List<FridgeItemDetails> map(List<FridgeItemEntity> entities);
}
