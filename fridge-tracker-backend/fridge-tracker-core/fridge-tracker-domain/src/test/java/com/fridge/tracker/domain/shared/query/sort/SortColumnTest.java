package com.fridge.tracker.domain.shared.query.sort;

import org.junit.jupiter.api.Test;

import static com.fridge.tracker.domain.shared.query.sort.enumeration.SortDirection.ASC;
import static org.junit.jupiter.api.Assertions.*;

class SortColumnTest {

    @Test
    void constructor_invalidProperty_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortColumn("name-d-asc"));
    }

    @Test
    void constructor_invalidDirection_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortColumn("name-assc"));
    }

    @Test
    void constructor_returnSortColumn() {
        SortColumn result = new SortColumn("name-asc");

        assertNotNull(result);
        assertEquals("name", result.getName());
        assertEquals(ASC, result.getDirection());
    }

}