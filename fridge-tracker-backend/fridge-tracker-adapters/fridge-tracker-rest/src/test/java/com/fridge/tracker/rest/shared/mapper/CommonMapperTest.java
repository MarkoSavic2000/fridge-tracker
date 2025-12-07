package com.fridge.tracker.rest.shared.mapper;

import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.rest.fridge.FridgeMapperImpl;
import com.fridge.tracker.rest.model.ResultResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonMapperTest {
    CommonMapper mapper = new FridgeMapperImpl();

    @Test
    void map_returnResultResponse() {
        ResultResponse result = mapper.map(new CreatedResult<>(null));

        assertNotNull(result);
        assertTrue(result.getSuccess());
    }
}