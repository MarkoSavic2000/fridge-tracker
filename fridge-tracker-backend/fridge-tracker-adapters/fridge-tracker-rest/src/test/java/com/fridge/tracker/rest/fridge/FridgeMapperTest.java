package com.fridge.tracker.rest.fridge;

import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.rest.fridge.mock.FridgeDetailsMock;
import com.fridge.tracker.rest.model.FridgeDetailsApi;
import com.fridge.tracker.rest.model.FridgePage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FridgeMapperTest {
    FridgeMapper mapper = new FridgeMapperImpl();

    @Test
    void map_returnFridgePage() {
        FridgeDetails details = FridgeDetailsMock.fridgeDetails;
        var input = new PageResult<>(List.of(details), 10, 0, 8, 80, true, false);

        FridgePage result = mapper.map(input);

        assertNotNull(result);
        List<FridgeDetailsApi> content = result.getContent();
        assertNotNull(content);
        assertEquals(1, content.size());
        assertFridgeDetails(details, content.getFirst());

        assertEquals(input.getPageNumber(), result.getPage());
        assertEquals(input.getPageSize(), result.getSize());
        assertEquals(input.getTotalPages(), result.getTotalPages());
        assertEquals(input.getTotalElements(), result.getTotalElements());
        assertEquals(input.isHasNext(), result.getHasNext());
        assertEquals(input.isHasPrevious(), result.getHasPrevious());
    }

    private void assertFridgeDetails(FridgeDetails expected, FridgeDetailsApi result) {
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getCreatedOn(), result.getCreatedOn());
    }

}