package com.fridge.tracker.persistence.sql.shared.query.mapper;

import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SortColumnsMapperTest {
    SortColumnsMapper mapper = new SortColumnsMapperImpl();

    @Test
    void map_returnSort() {
        var input = List.of(new SortColumn("name-asc"));

        Sort result = mapper.map(input);

        assertNotNull(result);

        Sort.Order order = result.getOrderFor("name");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }
}