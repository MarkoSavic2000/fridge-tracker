package com.fridge.tracker.rest.fridge_item;

import com.fridge.tracker.application.fridge_item.add.AddFridgeItemCommand;
import com.fridge.tracker.application.fridge_item.list.ListFridgeItemsQuery;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import com.fridge.tracker.rest.fridge_item.mock.AddFridgeItemRequestMock;
import com.fridge.tracker.rest.model.AddFridgeItemRequest;
import com.fridge.tracker.rest.model.FridgeItemCategoryEnumApi;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.fridge.tracker.domain.fridge_item.model.enumeration.FridgeItemCategoryEnum.DAIRY;
import static com.fridge.tracker.domain.shared.query.sort.enumeration.SortDirection.ASC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FridgeItemMapperTest {
    FridgeItemMapper mapper = new FridgeItemMapperImpl();

    @Test
    void map_returnAddFridgeItemCommand() {
        Long fridgeId = 1L;
        AddFridgeItemRequest request = AddFridgeItemRequestMock.request;

        AddFridgeItemCommand result = mapper.map(fridgeId, request);

        assertNotNull(result);

        FridgeItemRecord fridgeItem = result.getFridgeItem();
        assertNotNull(fridgeItem);
        assertEquals(fridgeId, fridgeItem.getFridgeId());
        assertEquals(request.getName(), fridgeItem.getName());
        assertEquals(request.getQuantity(), fridgeItem.getQuantity());
        assertEquals(request.getMeasurementUnit(), fridgeItem.getMeasurementUnit());
        assertEquals(request.getExpiresOn(), fridgeItem.getExpiresOn());
        assertEquals(request.getStoredOn(), fridgeItem.getStoredOn());
        assertEquals(request.getCategory().name(), fridgeItem.getCategory().name());
        assertEquals(request.getNote(), fridgeItem.getNote());
    }

    @Test
    void map_returnListFridgeItemsQuery() {
        Long fridgeId = 10L;
        Integer page = 1;
        Integer size = 20;
        List<String> sort = List.of("name-asc");
        String name = "Milk";
        FridgeItemCategoryEnumApi categoryApi = FridgeItemCategoryEnumApi.DAIRY;
        LocalDate storedFrom = LocalDate.of(2024, 1, 1);
        LocalDate storedTo = LocalDate.of(2024, 1, 10);
        LocalDate expiresFrom = LocalDate.of(2024, 2, 1);
        LocalDate expiresTo = LocalDate.of(2024, 2, 10);
        Boolean expired = false;

        ListFridgeItemsQuery result = mapper.map(
                fridgeId,
                page,
                size,
                sort,
                name,
                categoryApi,
                storedFrom,
                storedTo,
                expiresFrom,
                expiresTo,
                expired
        );

        FridgeItemQueryFilter filter = result.getFilter();

        assertEquals(page, filter.getPageNumber());
        assertEquals(size, filter.getPageSize());

        List<SortColumn> sortColumns = filter.getSortColumns();
        assertEquals(1, sortColumns.size());
        assertEquals("name", sortColumns.getFirst().getName());
        assertEquals(ASC, sortColumns.getFirst().getDirection());

        assertEquals(fridgeId, filter.getFridgeId());
        assertEquals(name, filter.getName());
        assertEquals(DAIRY, filter.getCategory());

        assertEquals(storedFrom, filter.getStoredOnFrom());
        assertEquals(storedTo, filter.getStoredOnTo());
        assertEquals(expiresFrom, filter.getExpiresOnFrom());
        assertEquals(expiresTo, filter.getExpiresOnTo());
    }
}