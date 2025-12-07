package com.fridge.tracker.persistence.sql.fridge_item.event.mapper;

import com.fridge.tracker.domain.fridge_item.event.FridgeItemEvent;
import com.fridge.tracker.persistence.sql.fridge_item.event.entity.FridgeItemEventEntity;
import com.fridge.tracker.persistence.sql.fridge_item.event.mock.FridgeItemEventMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FridgeItemEventEntityMapperTest {
    FridgeItemEventEntityMapper mapper = new FridgeItemEventEntityMapperImpl();

    @Test
    void map_nullInput_returnNull() {
        FridgeItemEventEntity result = mapper.map(null);

        assertNull(result);
    }

    @Test
    void map_returnEntity() {
        FridgeItemEvent input = FridgeItemEventMock.event;

        FridgeItemEventEntity result = mapper.map(input);

        assertNotNull(result);
        assertEquals(input.getFridgeItemId(), result.getFridgeItemId());
        assertEquals(input.getFridgeId(), result.getFridgeId());
        assertEquals(input.getUserId(), result.getUserId());
        assertEquals(input.getQuantity(), result.getQuantity());
        assertEquals(input.getType(), result.getType());
    }
}