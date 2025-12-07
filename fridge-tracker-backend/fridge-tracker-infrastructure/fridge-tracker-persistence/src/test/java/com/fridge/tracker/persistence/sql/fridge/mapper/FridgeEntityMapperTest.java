package com.fridge.tracker.persistence.sql.fridge.mapper;

import com.fridge.tracker.persistence.sql.fridge.entity.FridgeEntity;
import com.fridge.tracker.persistence.sql.fridge.mock.FridgeRecordMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FridgeEntityMapperTest {
    FridgeEntityMapper mapper = new FridgeEntityMapperImpl();

    @Test
    void map_nullInput_returnNull() {
        var input = FridgeRecordMock.create();

        FridgeEntity result = mapper.map(input);

        assertNotNull(result);
        assertEquals(input.getUserAccountId(), result.getUserAccountId());
        assertEquals(input.getName(), result.getName());
    }
}